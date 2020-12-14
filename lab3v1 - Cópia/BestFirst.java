
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


class BestFirst {
    static class State {
        private  Ilayout layout;
        private  State father;
        private List<State> childs=new ArrayList<>();
        private double g,h,f,s,w;
        private boolean final_node=false;

        public State( Ilayout l,  State n,Ilayout obj) throws CloneNotSupportedException {
            layout = l;
            father = n;
            h=l.getH(obj);
            if (father != null)
                g = father.g + l.getG();
            else
                g = 0.0;
            f=g+h;
            if(l.isGoal(obj)) final_node=true;
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

        private double uct(){
            if(s==0) return Integer.MAX_VALUE;
            return (w/s)*Math.sqrt(2)*Math.sqrt(Math.log(father.s)/s);
        }

        public State BestChild(){
            double t=0;
            State t1=null;
            for(State s:childs){
                if(s.uct()>t){
                    t1=s;
                    t=s.uct();
                }
            }
            return t1;
        }


    }

    protected Stack<State> abertos;
    private State actual,root;
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
        actual=new State(s,null,goal);
        objective=goal;
        root=actual;
        double h=root.h;
        System.out.println(actual.h);
        while(!actual.final_node){

            if(!actual.childs.isEmpty()){
                actual=selection(actual);
            }
            if(actual.final_node) break;
            if(actual.childs.isEmpty() && actual.g<root.h)
                actual.childs=sucessores(actual);
            //System.out.println(actual+" "+actual.g+" "+actual.s+" "+actual.w+" "+actual.childs.isEmpty());
            double v=simulation(actual);
            //System.out.println();
            backpropagation(actual,v);
            actual=root;
        }
        List<State> sol=new ArrayList<State>();
        while(actual!=null){
            sol.add(actual);
            actual=actual.father;
        }
        Collections.reverse(sol);
        return sol.iterator();
    }

    private void backpropagation(BestFirst.State actual2,double v) {
        while(actual2.father!=null){
            actual2=actual2.father;
            actual2.w+=v*actual.g;
            actual2.s++;
        }
        actual=actual2;
    }
    public State BestChildsHeuristc(List<State> l,State s){
        double h=s.h;
        State u=s;
        for(State t:l){
            if(t.h<h){
                u=t;
                h=u.h;
            }
        }
        return u;
    }
    private double simulation(BestFirst.State actual2) throws CloneNotSupportedException {
        State t=actual2;
        while(actual2.g<root.h){
            if(actual2.isGoal(objective) || (actual2.father!=null && actual2.h>=actual2.father.h)) break;
            List<State> s=sucessores(actual2);
            actual2=BestChildsHeuristc(s,actual2);
        }
        actual=t;
        if(actual2.isGoal(objective)){
            actual2.w=1;
            actual.w=actual.g/actual2.g;
        }
        actual.s++;
        return 1/actual.g;
    }

    private BestFirst.State selection(State s) {
        while(!s.childs.isEmpty() && s.g<root.h){
            s=s.BestChild();
        }
        return s;
    }
}