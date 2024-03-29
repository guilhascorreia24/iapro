import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
    private List<Stack<Character>> board = new ArrayList<Stack<Character>>();
    private double h = 0;
    public int blocks = 0;

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
       // if(blocks>7) throw new IllegalStateException("Invalid arg in Board constructor");
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
        Board nBoard = (Board)super.clone();
        nBoard.board=new ArrayList<>();
        nBoard.blocks=0;
        nBoard.h=0;
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
        @Override
        public double getH(Ilayout b) throws CloneNotSupportedException { // heuristica
            Board conf_final = (Board) b;
            HashMap<Character, Integer> mp = new HashMap<>();
            for (int s = 0; s < board.size(); s++) {
                Stack<Character> pilha_inicial = board.get(s);
                if (!conf_final.board.contains(pilha_inicial)) {
                    List<Character> under = new ArrayList<>();
                    for (int i = 0; i < pilha_inicial.size(); i++) {
                        Character c = pilha_inicial.get(i);
                        for (int k = 0; k < conf_final.board.size(); k++) {
                            Stack<Character> pilha_final = conf_final.board.get(k);
                            if (pilha_final.contains(c) && (mp.get(c) == null || mp.get(c) != 2)) {
                                if (pilha_final.indexOf(c) + i != 0) {
                                    Character c2 = pilha_final.firstElement();
                                    int j = 0, unders = 0, seq = 0;
                                    while (c2 != c) {
                                        if (under.contains(c2)) {
                                            if (pilha_final.indexOf(c2) == pilha_inicial.indexOf(c2)) {
                                                seq++;
                                            }
                                            unders++;
                                        }
                                        /*if (unders == 0 && !mp.containsKey(c) && board.size()!=1 && conf_final.board.size()!=1) { // situacao de pervesao mutua
                                            for (Stack<Character> s_inicial : board) {// analisar o bloco c2 na conf inicial
                                                if (s_inicial.contains(c2)) {//encontra-lo
                                                    for (int p = s_inicial.indexOf(c2)+1; p < s_inicial.size(); p++) {//analisar os blocs que estao a cima do bloc c2 na conf_inicial
                                                        Character c6 = s_inicial.get(p);//guardar
                                                        for (Stack<Character> s_final : conf_final.board) {//analisar os bloc c6 
                                                            if (s_final.contains(c6)) {//encontrar c6 na conf final
                                                                for (int t = 0; t < s_final.indexOf(c6); t++) {// analisar os blocos abaixo do c6 na conf final
                                                                    if (under.contains(s_final.get(t))) {// ver se os blcos na conf_final estao tb nos unders da conf inicial
                                                                        mp.put(c6, 1);//precisara de ser mover pelo menos 1 vez
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                        }*/
                                        j++;
                                        c2 = pilha_final.elementAt(j);
                                    }
                                    if (unders == 0) {
                                        if (mp.get(c) != null && mp.get(c) == 1)
                                            mp.put(c, 2);
                                        else
                                            mp.put(c, 1);
                                    } else {
                                        if (j == unders && j == under.size() && unders == seq) {
                                            // caso tenha o mesmo n de blocos debaixo e estejam nna mema seq
                                            mp.put(c, 0);
                                        } else {
                                            mp.put(c, 2);
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        under.add(c);
                    }
                }
            }
            for (Integer v : mp.values()) {
                h += v;
            }
            return h;
        }

    /**
     * indice de promessa, estimativa do custo do caminho
     */
    public double getF() { 
        return h + getG();
    }
}