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
        private double c = 0.4015;
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
        int playouts = 0, limit = 10000;// 1000
        while (playouts < limit) {
            if (!actual.childs.isEmpty()) {
                actual = selection(actual);
            }
            // System.out.println();
            if (!actual.final_node)
                actual.childs = expand(actual);
            actual = simulation(actual);
            // System.out.println(actual.s+"\n"+actual);
            for (State l : actual.childs) {
                // System.out.println(actual.childs.indexOf(l) + " s:" + l.s + " w:" +
                // l.w+"\n"+l);
            }

            playouts++;
            max = true;
        }
        //System.out.println(root.childs);
        actual = bestmove(root);
        if (actual.final_node)
            end_game = true;
        // System.out.println(actual.max+" "+actual.layout.getplayer()+"
        // "+root.layout.getplayer());
        return (Board) actual.layout;
    }

    private State bestmove(State s) throws CloneNotSupportedException {
        for (State l : s.childs) {
            //System.out.println("s:" + l.s + " w:" + l.w + " uct:" + l.uct() + "\n" + l);
            for (State l1 : l.childs) {
              //  System.out.println("s:"+l1.s+" w:"+l1.w+"\n"+l1);
            }
            //System.out.println("###################");
        }
        // if(s.childs.size()==1) return s.childs.get(0);
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
        int w = actual.layout.stateBoard();

        for (State suc : s.childs) {
            s = suc;
            while (!s.final_node) {
                List<State> sucs = expand(s);
                // System.out.println((int)Math.random()*sucs.size());
                int rn = (int) (new Random().nextInt(sucs.size()));
                // System.out.println(rn);
                s = sucs.get(rn);
            }
            w = s.layout.stateBoard();

            actual = backpropagation(s, w, 1);
        }
        if (actual.final_node)
            actual = backpropagation(actual, w, 1);
        return actual;
    }

    private double score(int x) {
        if (x > 0) {
            return 1 ;
        }
        return 0;
    }

    private State backpropagation(State actual2, int w, int ii) {
        double score = score(w);
        if (actual2.layout.getplayer() == root.layout.getplayer()) {
            score = -score;
        }
        boolean p = true;
        while (actual2.father != root.father) {
            // if(score>0)
            actual2.setWin(score);
            //score=-score;
            actual2.s += 1;
            p = !p;
            actual2 = actual2.father;
        }
        actual2.s += 1;
        return actual2;
    }

    private State selection(State s) {
        boolean t = true;
        while (!s.childs.isEmpty()) {
            if (max) {
                s = s.BestUCT();
                if (t) {

                    for (State p : root.childs) {
                        // System.out.println(p.s + " " + p.w + " " + p.uct());
                    }
                    // System.out.println();
                    // System.out.println(root.childs.indexOf(s)+" maior "+s.max+"\n");
                }
                max = false;
            } else {
                State u = s;
                s = s.WorstUCT();
                if (t) {

                    for (State p : u.childs) {
                        // System.out.println(p.s + " " + p.w + " " + p.uct());
                    }
                    // System.out.println();
                    // System.out.println(u.childs.indexOf(s)+" menor "+s.max+"\n");
                    t = false;
                    // System.out.println(s.max);
                }
                max = true;
            }
        }
        return s;
    }
}