import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class MCTS {
    static class State {
        public Ilayout layout;
        public State father;
        public List<State> childs = new ArrayList<>();
        public double s, w;
        private boolean final_node = false;
        private double c =Math.sqrt(0.0241);
        private int max = Integer.MAX_VALUE;
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
                //max = -n.max;
            }
        }

        public boolean isfinalnode() {
            return final_node;
        }

        public String winner(){
            if(layout.getplayer()=='O' && layout.stateBoard()==1){
                return "win O";
            }else if (layout.getplayer()=='X' && layout.stateBoard()==1){
                return "win X";
            }else{
                return "Draw";
            }
        }
        
        private void setWin(double score) {
            this.w += score;
        }

        public String toString() {
            return layout.toString();
        }

        public double uct() {
            //System.out.println(w+" "+s);
            if (s == 0)
                return max;
            if(w<0 || s<0 || (father!=null && father.s<1)){
                throw new IllegalArgumentException("wins negative");
            }
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
    public boolean end_game = false;

    final public List<State> expand(State n) throws CloneNotSupportedException { // listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            State nn = new State(e, n);
            sucs.add(nn);
        }
        return sucs;
    }
    final public Iterator<State> solve(Ilayout s) throws CloneNotSupportedException {
        List<State> l=new ArrayList<>();
        State k=new State(s,null);
        l.add(k);
        while(!end_game){
            k=BestNextMove(k.layout);
            l.add(k);
        }
        return l.iterator();
        
    }
    final public State BestNextMove(Ilayout s) throws CloneNotSupportedException { // algoritmo mcts
        actual = new State(s, null);
        if (actual.final_node) {
            end_game = true;
            return actual;
        }
        root = actual;
        float playouts = 0, limit = 10000;// 50000
        while (playouts < limit) {
            ;
            if (!actual.childs.isEmpty()) {
                actual = selection(actual);
            }
            if (!actual.final_node)
                actual.childs = expand(actual);
            actual = simulation(actual);

            playouts++;;
        }
        actual = bestmove(root);
        if (actual.final_node)
            end_game = true;
        return actual;
    }

    private State bestmove(State s) throws CloneNotSupportedException {
        /*for(State s1:s.childs){
            System.out.println(s1.uct()+" "+s1.w+" "+s1.s+"\n"+s1);
        }*/
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
        int w = root.childs.get(0).layout.verifywinner(actual.layout);

        for (State suc : s.childs) {
            s = suc;
            while (!s.final_node) {
                List<State> sucs = expand(s);
                // System.out.println((int)Math.random()*sucs.size());
                int rn = (int) (new Random().nextInt(sucs.size()));
                // System.out.println(rn);
                s = sucs.get(rn);
            }
            w = root.childs.get(0).layout.verifywinner(s.layout);

            actual = backpropagation(suc, w, 1);
        }
        if (actual.final_node)
            actual = backpropagation(actual, w, 1);
        return actual;
    }

    private double score(int x) {
        if (x == 1) {
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
        boolean max = true;
        while (!s.childs.isEmpty()) {
            if (max) {
                s = s.BestUCT();
                max = false;
            } else {
                s = s.WorstUCT();
                max = true;
            }
        }
        return s;
    }
}