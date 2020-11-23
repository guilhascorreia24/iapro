import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class BestFirst {
    static class State {
        private  Ilayout layout;
        private  State father;
        private double g;

        public State( Ilayout l,  State n) {
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

        /*@Override
        public boolean equals( Object b){
             State b2=(State)b;
            return layout.equals(b2.layout);
        }*/
    }

    protected Queue<State> abertos;
    private State actual;
    private Ilayout objective;

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

    final public Iterator<State> solve(Ilayout s) throws CloneNotSupportedException { // algoritmo bfs
        return null;
    }
}