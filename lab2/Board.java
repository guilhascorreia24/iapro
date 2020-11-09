import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

//import org.graalvm.compiler.phases.common.NodeCounterPhase.Stage;

interface Ilayout {
    /**
     * @return the children of the receiver.
     * @throws CloneNotSupportedException
     */
    List<Ilayout> children() throws CloneNotSupportedException;

    /**
     * @return true if the receiver equals the argument l; return false otherwise.
     */
    boolean isGoal(Ilayout l);

    /**
     * @return the cost for moving from the input config to the receiver.
     */
    double getG();

    double getH(Ilayout b) throws CloneNotSupportedException;

    double getF();

}

class Board implements Ilayout, Cloneable {
    // private int dim;
    private LinkedList<Stack<Character>> board = new LinkedList<Stack<Character>>();
    private int h = 0;
    int blocks = 0;
    
    /**
     * Board vazia 
     */
    public Board() {
    }

    /**
     * 
     * @param str recebe uma string que representa a board
     * @throws IllegalStateException caso tenha mais que o numero de blocks necessarios
     */
    public Board(String str) throws IllegalStateException {
        String[] b2 = str.split(" ");
        for (int i = 0; i < b2.length; i++) {
            if (!b2[i].equals("")) {
                int j = 0;
                Stack<Character> line = new Stack<Character>();
                while (j < b2[i].split("").length) {
                    line.add(b2[i].charAt(j));
                    j++;
                }
                board.add(line);
                blocks += j;
            }
        }
        //if(blocks>7) throw new IllegalStateException("Invalid arg in Board constructor");
    }

    /**
     * representa a board, imprime
     */
    public String toString()
    {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        for(int i = 0; i < board.size(); i++)
            if(!board.get(i).isEmpty())
                pw.println(board.get(i));
        pw.close();
        return writer.toString();
    }

    /**
     * Clona a board actual
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Board nBoard = new Board();
        for (int i = 0; i < board.size(); i++) {
            int j = 0;
            Stack<Character> line = new Stack<Character>();
            while (j < board.get(i).size()) {
                line.add(board.get(i).elementAt(j));
                j++;
            }
            nBoard.board.add(line);
        }
        // nBoard.dim=nBoard.board.size();
        return nBoard;
    }

    /**
     * Criar todos os filhos da board actual
     */
    @Override
    public List<Ilayout> children() throws CloneNotSupportedException { // criar os filhos
        List<Ilayout> children = new ArrayList<Ilayout>();
        for (int i = 0; i < board.size(); i++) {
            Board child = (Board) clone();
            char block = child.board.get(i).pop();
            if (child.board.get(i).isEmpty())
                child.board.remove(i);

            for (int j = 0; j < child.board.size(); j++) {
                Board cloneChild = (Board) child.clone();
                cloneChild.board.get(j).push(block);
                if (!children.contains(cloneChild) && !cloneChild.equals(this)) {
                    children.add(cloneChild);
                }
            }

            Stack<Character> last = new Stack<Character>();
            last.push(block);
            child.board.add(last);
            if (!children.contains(child) && !child.equals(this)) {
                children.add(child);
            }
        }
        return children;
    }  

    /**
     * Compara a board actual com a l
     */
    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    /**
     * Compara a board actual com a l
     */
    @Override
    public boolean equals(Object b) { // compara 2 boards
        if (this.getClass().equals(b.getClass())) {
            Board b1 = (Board) b;
            // System.out.println(this);
            for (Stack<Character> x : board) {
                // System.out.println(b1.board.contains(x));
                if (!b1.board.contains(x)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * representa o custo do move
     */
    @Override
    public double getG() { // custo
        return 1;
    }

        /**
     * 
     * representa o custo previsto da board actual ate a board b, usando a seguinte estrategia:
     * 1)Ve a config incial um bloco
     * 2)Quando vai verificar se esse bloco existe na conf final ele verifica logo se existe um stack igual 
     * a inicial e ignora logo essa stack
     * 3)No caso de a stack nao existir na conf final, vamos verificar a stack onde se encontra o bloco que 
     * encontramos na conf inicial e vemos as seguintes caracteristicas
     *   3.1)Caso o bloco seja uma base na stack da conf inicial e na final, e somado a heuristica 0
     *   3.2)Caso esse bloco na stack da config inicial tenha +1 bloco debaixo dele que estejam tambem na stack da
     *    config final e somado +2, visto que esse bloco tera de ir ao chao e subir para cima da nova stack, caso 
     *    nessa stack config final tenha o mesmo numero de blocos e esses estejam na mesma sequencia da conf inicial 
     *    e somado 0 a heuristica
     *   3.3)No caso de nenhuma das outras 2 condicoes acontecer e somado +1 a heuristica, 
     */
    /*@Override
    public double getH(Ilayout b) throws CloneNotSupportedException { // heuristica
        Board conf_final = (Board) b;
        for (Stack<Character> pilha_inicial : board) {
            if (!conf_final.board.contains(pilha_inicial)) {
            List<Character> under = new ArrayList<>();
            for (int i=0;i<pilha_inicial.size();i++) {
                Character c=pilha_inicial.get(i);
                    for (Stack<Character> pilha_final : conf_final.board) {
                        if (pilha_final.contains(c)) {
                            //System.out.println(pilha_final.indexOf(c)+" "+i);
                            if(pilha_final.indexOf(c)+i==0){
                                h+=0;
                            }else{
                                Character c2=pilha_final.firstElement();
                                int j=1,unders=0,seq=0;
                                while(c2!=c){
                                    if(under.contains(c2)){
                                        if(pilha_final.indexOf(c2)==pilha_inicial.indexOf(c2)){
                                            //System.out.println(pilha_final.indexOf(c2)+" "+pilha_inicial.indexOf(c2)+" "+c2);
                                            seq++;
                                        }
                                        unders++;
                                    }
                                    c2=pilha_final.elementAt(j);
                                    j++;
                                }
                                //System.out.println(under.size()+" "+unders+" "+(j-1)+" "+seq);
                                 if(unders==0){
                                    h++;
                                }else{
                                    if(under.size()==unders && j-1==under.size() && unders==seq) h+=0; // caso tenha o mesmo nº de blocos debaixo e estejam nna mema seq 
                                    else h+=2;
                                }
                            }
                        }
                    }
                    //System.out.println(c+" "+h);
                    under.add(c);
                }
            }
        }
        return h;
    }*/

    /**
     * representa o custo previsto da board actual ate a board b, usando a seguinte estrategia:
     * 1)Ve a config incial um bloco
     * 2)Quando vai verificar se esse bloco existe na conf final ele verifica logo se existe um stack igual 
     * a inicial e ignora logo essa stack
     * 3)Caso a stack nao exista na conf final, vamos verificar a stack onde se encontra o bloco que 
     * encontramos na conf inicial e vemos as seguintes caracteristicas
     *   3.1)Na conf inicial caso esteja um bloco no solo e esse mesmo bloco na conf final esteja noutra  posicao sem ser o solo
     *    adicionamos +1, pois esse bloco ira so fazer 1 movimentacao, o mesmo exemplo aplica-se quando na conf inicial o bloco
     *    encontra-se numa posicao sem ser o solo e na conf final ele descola-se para o solo
     *   3.2)Caso esse bloco na stack da config inicial tenha +1 bloco debaixo dele que estejam tambem na stack da
     *    config final e somado +2, visto que esse bloco tera de ir ao chao e subir para cima da nova stack, caso 
     *    nessa stack da config final tenha o mesmo numero de blocos e esses estejam na mesma seqquencia da conf inicial 
     *    despresamos esse caso porque nao houve nenhuma alteracao nos blocos
     */
    @Override
    public double getH(Ilayout b) throws CloneNotSupportedException {  // heuristica 2º versao
        Board conf_final = (Board) b;
        int counter = 0;
        for(int i = 0; i < board.size(); i++)
        {
            if(!conf_final.board.contains(board.get(i)))
            {
              List<Character> under_initial = new ArrayList<>();
                for(int j = 0; j < board.get(i).size(); j++)
                {
                  under_initial.add(board.get(i).get(j));
                    Character c = board.get(i).elementAt(j);
                    for(int k = 0; k < conf_final.board.size(); k++)
                    {
                        List<Character> under_final = new ArrayList<>();
                        for(int l = 0; l < conf_final.board.get(k).size(); l++)
                        {
                            under_final.add(conf_final.board.get(k).get(l));
                            Character c2 = conf_final.board.get(k).elementAt(l);
                            if(c == c2)
                            {
                                if(j != 0 && l == 0)
                                {
                                    counter++;
                                }
                                else if(j == 0 && l != 0)
                                {
                                    counter++;
                                }
                                else if(j != 0 && l != 0)
                                {
                                    if(!under_initial.equals(under_final))
                                    {
                                        int m = 0;
                                        boolean help = false;
                                        while(m < under_initial.size()-1)
                                        {
                                            if(under_final.contains(under_initial.get(m)))
                                            {
                                                help = true;
                                                break;
                                            }
                                            m++;
                                        }
                                        if(help == true)
                                            counter+=2;
                                        else
                                            counter+=1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return counter;
    }

    /**
     * indice de promessa, estimativa do custo do caminho
     */
    public double getF() { 
        return h + getG();
    }
}