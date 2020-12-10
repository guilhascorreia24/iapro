import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class MCTS {
    static class State {
        private Ilayout layout;
        public State father;
        public List<State> childs = new ArrayList<>();
        public double s, w, l, c = Math.sqrt(2),uct;
        private boolean final_node = false;
        private int max = Integer.MAX_VALUE;
        public int g;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            s = 0.0;
            w = 0;
            this.l = 0.0;
            if (layout.stateBoard() != -2) {
                final_node = true;
            }
            if (n == null)
                g = 0;
            else {
                g = n.g + 1;
            }
        }

        public void setScore(double x) {
            if (x > 0)
                w += 1;
            else if (x < 0){
                l+=1;
            }
            else
                w+=1;
        }   

        public boolean isfinalnode() {
            return final_node;
        }

        public String toString() {
            return layout.toString();
        }

        public double uct() {
                if (s == 0)
                    return (double) max;
                uct = w / s + c * Math.sqrt(Math.log(father.s) / s);
            return uct;
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
            double media=0;
            for(State s:childs){
                media+=s.s;                // igualar as simulaçoes dos filhos para dar oportunidade de visitar os filhos deles
            }
            media=media/childs.size();
            List<State> low_media=new ArrayList<>();
            for(State s:childs){
                if(s.s<=media){
                    low_media.add(s);
                }
            }
            return Collections.min(low_media, new Comparator<State>() {   // ver o uct menor dos filhos com visitas abaixo da media 
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
            // State nn=anycousin(n.father,e);
            // nn.father=n;
            State nn = new State(e, n);
            sucs.add(nn);
        }
        return sucs;
    }

    final public Board BestNextMove(Ilayout s) throws CloneNotSupportedException { // algoritmo bfs
        if (actual == null)
            actual = new State(s, null);
        
          else { if (actual.childs.contains(new State(s, null))) { actual =
          actual.childs.get(actual.childs.indexOf(new State(s, null))); } }
         
        List<Double> inicio_s=new ArrayList<>(), inicio_w=new ArrayList<>();
        if(!actual.childs.isEmpty()){
        for (State g : actual.childs) {
            //System.out.println(g.w + " " + g.s + " "+ g.uct+"\n" + g);
            inicio_s.add(g.s);
            inicio_w.add(g.w);
            }
        //System.out.println("#############################");
        }
        root = actual;
        int playouts = 0, limit = 10;// 1000 // 370
        while (playouts < limit) {
            if (!actual.childs.isEmpty()) {
                actual = selection(actual);
            }
            if (!actual.final_node) {
                actual.childs = expand(actual);
                simulation(actual);
            } else {
                backpropagation(actual, actual.layout.stateBoard());
            }
            actual = root;
            playouts++;
        }
        actual = bestmove(root,inicio_s,inicio_w);
        if (actual.final_node)
            end_game = true;
        return (Board) actual.layout;
    }

    private State selection(MCTS.State actual2) {
        boolean max = true, t = true;
        while (!actual2.childs.isEmpty()) {
            if (max) {
                actual2 = actual2.BestUCT();
                max = false;
            } else {
                actual2 = actual2.WorstUCT();
                max = true;
            }

        }

        return actual2;
    }

    private void simulation(MCTS.State actual2) throws CloneNotSupportedException {
        for (State s : actual2.childs) {
            actual = s;
            while (!actual.isfinalnode()) {
                List<State> sucs = expand(actual);
                int rng = (int) (Math.random() * sucs.size());
                actual = sucs.get(rng);
            }
            actual = backpropagation(s, s.layout.verifywinner(actual.layout));
        }
    }

    private MCTS.State backpropagation(State actual2, double win) {
        double n = (actual.g - actual2.g) * 0.05;
        if (win == 0)
            win = 0.5;
        while (actual2 != root) {
            if (win > 0.5)
                win = win - n;
            else if (win < 0.5)
                win = win + n;
            actual2.setScore(win);
            actual2.s += 1;
            actual2 = actual2.father;
            if (win != 0.5) {
                win = -win;
                n = 0.05;
            }
        }
        actual2.s += 1;
        return actual2;
    }

    private State bestmove(State s,List<Double> inicio,List<Double> inicio_w)throws CloneNotSupportedException {
        List<Double> u=new ArrayList<>();
        for (int i=0;i<s.childs.size();i++) {
            if(inicio.size()!=s.childs.size()){
                inicio.add(0.0);
                inicio_w.add(0.0);
            }
           // System.out.println(s.childs.get(i).w+" "+inicio_w.get(i) +" " + (s.childs.get(i).s+" "+inicio.get(i))+" "+ s.childs.get(i).uct);
            u.add((s.childs.get(i).s-inicio.get(i)));
        }
        int i=u.indexOf(Collections.max(u));
        return s.childs.get(i);
    }

}