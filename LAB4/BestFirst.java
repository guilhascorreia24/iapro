import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;

class BestFirst {
    static class State {
        private  Ilayout layout;
        private  State father;
        private List<State> childs;
        private final double c=Math.sqrt(2);
        private double w,n;

        public State(Ilayout l,  State n) {
            layout = l;
            father = n;
        }

        public String toString() {
            return layout.toString();
        }

        public double ucb(){
            return (w/n)+c*Math.sqrt(Math.log(father.n)/n);
        }


    }

    protected Queue<State> abertos;
    private State actual;
    private int lvl=2;


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
        while(lvl<3){
            if(actual.father!=null)
                selection();
            expand();
            simulation();
            back();
        }

    }

    private void back() {
        int i=0;
        while(actual.father!=null){
            actual.father.w+=actual.w;
            actual.father.n+=actual.n;
            actual=actual.father;
            i++;
        }
        lvl=i;
    }

    private void selection() {
        double max=Integer.MIN_VALUE;
        for(State s:actual.childs){
            if(s.ucb()>max){
                max=s.ucb();
                actual=s;
            }
        }
    }

    private void expand() throws CloneNotSupportedException {
        actual.childs=sucessores(actual);
    }

    private void simulation() throws CloneNotSupportedException {
        State sim=actual;
        while(!actual.layout.full_board()){
            List<State> sucs=sucessores(sim);
            int rnd=new Random().nextInt(sucs.size());
            sim=sucs.get(rnd);
        }
        actual.w=sim.layout.verification();
        actual.n++;
    }
}