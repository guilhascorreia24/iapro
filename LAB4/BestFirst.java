import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;

class BestFirst {
    static class State {
        private Ilayout layout;
        private State father;
        private List<State> childs=new ArrayList<State>();
        private final double c = Math.sqrt(2.3);
        private double w, n;
        private int pc;

        public State(Ilayout l, State n,int pc) {
            layout = l;
            father = n;
            this.pc=pc;
            layout.your_simbol(pc);
        }

        public String toString() {
            return layout.toString();
        }

        public double ucb() {
            if (n == 0 || Math.log(father.n)==0) {
                return Double.MAX_VALUE;
            }
            return (w / n) + c * Math.sqrt(Math.log(father.n) / n);
        }


    }

    protected Queue<State> abertos;
    private State actual;
    private final static int lvl = 600000;

    final private List<State> sucessores(State n) throws CloneNotSupportedException { // listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n,(n.pc+1)%2);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    final public Ilayout mcts(Ilayout s, int pc) throws CloneNotSupportedException { // algoritmo bfs
        actual = new State(s, null,pc);
        int i = 0;
        while (i < lvl) {
            if (!actual.childs.isEmpty())
                selection(actual,0,Double.MIN_VALUE);
            //System.out.println(actual);
            expand(actual);
            //System.out.println(actual.childs);
            actual=simulation(actual);
            //System.out.println(actual.n+" "+actual.w);
            back(actual);
            i++;
        }
        double max = Double.MIN_VALUE;
        for (State suc : actual.childs) {
            if (suc.ucb() > max) {
                max = suc.ucb();
                actual = suc;
            }
            //System.out.println(suc.n+" "+suc.w+" "+actual.n+" "+suc.ucb()+" "+max);
            //System.out.println(actual);
        }
        return actual.layout;
    }

    private void back(State s) {
        while (actual.father != null) {
            actual.father.w += actual.w;
            actual.father.n += actual.n;
            actual = actual.father;
        }
    }

    private void selection(State s,int pc,double max) {
        for (State suc : actual.childs) {
            if (suc.ucb() > max && pc%2==0) {
                max = suc.ucb();
                actual = suc;
            }else if(suc.ucb()<max && pc%2!=0 ){
                max = suc.ucb();
                actual = suc;
            }
        }
        if (!actual.childs.isEmpty()) {
            pc++;
            if(pc%2!=0) max=Double.MAX_VALUE;
            else max=Double.MIN_VALUE;
            selection(actual,pc,max);
        }
    }

    private void expand(State s) throws CloneNotSupportedException {
        actual.childs = sucessores(actual);
    }

    private State simulation(State sim) throws CloneNotSupportedException {
        for (State suc : sim.childs) {
            State s=suc;
            while (!s.layout.full_board()) {
                List<State> sucs = sucessores(s);
                int rnd = (int) (sucs.size() * Math.random());
                // System.out.println(rnd+" "+sucs.size());
                s = sucs.get(rnd);
            }
            suc.w+=s.layout.winningVerification();
            suc.n+=1;
            sim.w+=suc.w;
            sim.n+=1;
            //System.out.println(s);
        }
    return sim;
    }
}