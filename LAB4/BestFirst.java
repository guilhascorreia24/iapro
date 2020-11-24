import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

class BestFirst {
    static class State {
        private  Ilayout layout;
        private  State father;
        private List<State> childs;
        private final double c=Math.sqrt(2);
        private double w,N,n,g;

        public State(Ilayout l,  State n) {
            layout = l;
            father = n;
            if (father != null)
                g = father.g + l.getG();
            else
                g = 0.0;
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        public double ucb(){
            return (w/n)+c*Math.sqrt(Math.log(N)/n);
        }


    }

    protected Queue<State> abertos;
    private State actual;

    final private List<State> sucessores( State n) throws CloneNotSupportedException { //listar os filhos que interessam
         List<State> sucs = new ArrayList<>();
         List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    final public Iterator<State> mcts(Ilayout s) throws CloneNotSupportedException { // algoritmo bfs
        actual=new State(s,null);
        while(true){
            if(actual.childs.isEmpty()){
                List<State> sucs=sucessores(actual);
                actual.childs=sucs;
            }
            if(actual.father!=null){
                int i=0;double ucb_min=Integer.MAX_VALUE;
                while(i<actual.childs.size()){
                    if(ucb_min<actual.childs.get(i).ucb()){
                        actual=actual.childs.get(i);
                        ucb_min=actual.childs.get(i).ucb();
                    }
                }
            }
            simulation();
        }

    }

    private void simulation(BestFirst.State actual2) {
        
    }
}