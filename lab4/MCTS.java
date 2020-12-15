import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

class MCTS {
    static class State {
        private Ilayout layout;
        public State father;
        public List<State> childs = new ArrayList<>();
        public double s, w;
        private boolean final_node = false;
        private double c = 0.4013;
        private int max = -Integer.MAX_VALUE;
        public int g;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            s = 0.0;
            w = 0;
            if (layout.stateBoard() != -2) {
                final_node = true;
            }
            if (n == null)
                g = 0;
            else {
                g = n.g + 1;
                max = -n.max;
            }
        }

        public boolean isfinalnode() {
            return final_node;
        }

        public void setWin(double score) {
            this.w += score;
        }

        public String toString() {
            return layout.toString();
        }

        public double uct() {
            if (s == 0)
                return max;
            return (w / s) + c * Math.sqrt(Math.log(father.s) / s);
        }

        @Override
        public boolean equals(Object b) {
            State s = (State) b;
            return layout.equals(s.layout);
        }

        public State BestUCT() {
            /*for(State s1:childs){
                if(s1.w<0)
                    System.out.println("skjds");
            }*/
            return Collections.max(childs, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if (z1.uct() > z2.uct())
                        return 1;
                    if (z1.uct() < z2.uct())
                        return -1;
                    return 0;
                }
            });
        }

        public State WorstUCT() {
            /*for(State s1:childs){
                if(s1.w<0)
                    System.out.println("skjds");
            }*/
            return Collections.min(childs, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if (z1.uct() > z2.uct())
                        return 1;
                    if (z1.uct() < z2.uct())
                        return -1;
                    return 0;
                }
            });
        }

    }

    private State actual, root;
    public boolean end_game = false, max = true;

    final public List<State> expand(State n) throws CloneNotSupportedException { // listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            State nn = new State(e, n);
            sucs.add(nn);
        }
        return sucs;
    }

    final public Board BestNextMove(Ilayout s) throws CloneNotSupportedException { // algoritmo bfs
        if (actual == null)
            actual = new State(s, null);
        else {
            if (actual.layout != s) {
                actual = new State(s, actual);
            } else
                actual = new State(s, actual.father);
            actual.max = -Integer.MAX_VALUE;
        }
        if (actual.final_node) {
            end_game = true;
            return (Board) actual.layout;
        }
        root = actual;
        int playouts = 0, limit = 80000;// 50000
        while (playouts < limit) {
            if (!actual.childs.isEmpty()) {
                actual = selection(actual);
            }
            if (!actual.final_node)
                actual.childs = expand(actual);
            actual = simulation(actual);

            playouts++;
            max = true;
        }
        actual = bestmove(root);
        if (actual.final_node)
            end_game = true;
        return (Board) actual.layout;
    }

    private State bestmove(State s) throws CloneNotSupportedException {
        State res = Collections.max(s.childs, new Comparator<State>() {
            @Override
            public int compare(State z1, State z2) {
                if (z1.s > z2.s)
                    return 1;
                if (z1.s < z2.s)
                    return -1;
                return 0;
            }
        });
        return res;
    }

    private State simulation(State s) throws CloneNotSupportedException {
        actual = s;
        int w = -root.layout.verifywinner(actual.layout);

        for (State suc : s.childs) {
            s = suc;
            while (!s.final_node) {
                List<State> sucs = expand(s);
                // System.out.println((int)Math.random()*sucs.size());
                int rn = (int) (new Random().nextInt(sucs.size()));
                // System.out.println(rn);
                s = sucs.get(rn);
            }
            w = -root.layout.verifywinner(s.layout);

            actual = backpropagation(suc, w, 1);
        }
        if (actual.final_node)
            actual = backpropagation(actual, w, 1);
        return actual;
    }

    private double score(int x) {
        if (x > 0) {
            return 1 ;
        }else if(x==0)
            return 0.5;
        else
            return 0;
    }

    private State backpropagation(State actual2, int w, int ii) {
        double score = score(w);
        while (actual2.father != root.father) {
            // if(score>0)
            actual2.setWin(score);
            //score=-score;
            actual2.s += 1;
            actual2 = actual2.father;
        }
        actual2.setWin(score);
        actual2.s += 1;
        return actual2;
    }

    private State selection(State s) {
        boolean t = true;
        while (!s.childs.isEmpty()) {
            if (max) {
                s = s.BestUCT();
                max = false;
            } else {
                State u = s;
                s = s.WorstUCT();
                max = true;
            }
        }
        return s;
    }
}