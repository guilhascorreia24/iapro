
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

class BestFirst {
    static class State {
        private  Ilayout layout;
        private  State father;
        private double g,h,f;

        public State( Ilayout l,  State n,Ilayout obj) throws CloneNotSupportedException {
            layout = l;
            father = n;
            h=l.getH(obj);
            if (father != null)
                g = father.g + l.getG();
            else
                g = 0.0;
            f=g+h;
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        public boolean isGoal(Ilayout obj){
            return this.layout.isGoal(obj);
        }

        public double getF(){
            return f;
        }

        public double getH(){
            return h;
        }
    }

    //protected Queue<State> abertos;
    private State actual;
    private Ilayout objective;
    //private double max_h;
    
    /***
     * Gera os filhos do no atual
     * @param n estado actual
     * @return retorna a lista de filhos que o estado actual tem, expecto os que sao iguais ao estado atual
     * @throws CloneNotSupportedException caso nao consiga clonar uma configuracao
     */
    final private List<State> sucessores(State n) throws CloneNotSupportedException { // listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n,objective);
                sucs.add(nn);
            }
        }
        return sucs;
    }
   

    /***
     * Esta funcao, utiliza o algoritmo A*, que consiste em pesquisar pelo estado com valor menor de F (g+h) que esta na lista 
     * ordenada abertos, e os casos que sao avaliados vao para lista fechados para nao serem pesquisados outra vez
     * @param s, configuracao inicial 
     * @param goal, configuracao final
     * @return retorna a lista com a sequencia de passos minimos para chegar a configuracao final
     * @throws CloneNotSupportedException, caso nao consiga clonar uma configuracao
     */
    final public Iterator<State> solve(Ilayout s, Ilayout goal) throws CloneNotSupportedException { // algoritmo bfs
        objective = goal;
        Queue<State> abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getF() - s2.getF()));
        List<State> fechados = new ArrayList<>();
        State root=new State(s, null,objective);
        abertos.add(root);
        actual=abertos.element();
        List<State> sucs;
        while(!actual.isGoal(goal)){
            if(abertos.isEmpty()){
                throw new IllegalStateException("Fail");
            }
            actual=abertos.poll();
            if(actual.layout.equals(objective)){
                break;
            }else{
                sucs=sucessores(actual);
                fechados.add(actual);
                for(State suc:sucs){
                    if(!fechados.contains(suc)){
                        abertos.add(suc);
                    }
                }
            }
        }
        List<State> sol=new ArrayList<State>();
        while(actual!=null){
            sol.add(actual);
            actual=actual.father;
        }
        Collections.reverse(sol);
        return sol.iterator();
    }

    /**
     * Esta funcao e um algoritmo de pesquisa chamado IDA*, identico ao A* mas neste caso pesquisa ate uma 
     * certa profunfidade que muda ao logo conforme cada profundidade (IDS) que nao e uniforme segue a profundidade 
     * da configuracao menor, percorre todos os filhos possiveis que tenham um valor indice de promessa menor que do actual.
     * @param s configuracao incial
     * @param goal  configuracao final
     * @return retorna a sequencia de passos minimos da conf inicial ate conf final
     * @throws CloneNotSupportedException
     */
    public Iterator<State> Ida(Ilayout s,Ilayout goal) throws CloneNotSupportedException {
        objective=goal;
        State root=new State(s,null,objective);
        double thres=root.getH();
        Stack<State> abertos=new Stack<>();
        abertos.add(root);
        while(true){
            actual=search(abertos,thres);
            if(actual.isGoal(objective)) break;
            thres=actual.f;
            System.out.println(actual.g);
        }
        List<State> sol=new ArrayList<State>();
        while(actual!=null){
            sol.add(actual);
            actual=actual.father;
        }
        Collections.reverse(sol);
        return sol.iterator(); 
    }


    /**
     * 
     * @param abertos lista de configuracoes nao vista 
     * @param thres valor maximo da profundidade 
     * @return retorna a configuracao com profindade maxima a que pode pesquisar
     * @throws CloneNotSupportedException
     */
    private BestFirst.State search(Stack<BestFirst.State> abertos, double thres) throws CloneNotSupportedException {
        actual=abertos.lastElement();
        if(actual.f>thres){
            return actual;
        }
        if(actual.isGoal(objective)){
            return actual;
        }
        State min_state=new State(actual.layout,actual.father,objective);
        List<State> sucs=sucessores(actual);
        for(State s:sucs){
            if(!abertos.contains(s)){
                abertos.push(s);
                actual=search(abertos, thres);
                if(actual.isGoal(objective)){
                    return actual;
                }
                if(actual.f<min_state.f){
                    min_state=actual;
                }
                abertos.pop();
            }
        }
        return min_state;
    }
}