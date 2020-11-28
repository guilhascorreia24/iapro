import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


class BestFirst {
    static class State {
        public Ilayout layout;
        public State father;
        private List<State> childs = new ArrayList<State>();
        private final double c = Math.sqrt(2);
        public double w = 0, n = 0;
        public int pc;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if(n==null) pc=0;
            else pc=n.pc+1;
            layout.your_simbol(pc+1);
        }

        public String toString() {
            return layout.toString();
        }

        public void score(double x){
            if(x>0){
                w+=x;
            }
        }

        public double uct() {
            if (n == 0 || Math.log(father.n) == 0 ) {
                if(father.pc%2==0) return Double.MAX_VALUE;
                else return -Double.MAX_VALUE;
            }
            return (w / n) + c * Math.sqrt(Math.log(father.n) / n);
        }


    }

    final private List<State> sucessores(State n) throws CloneNotSupportedException { // listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                // System.out.println(nn.pc);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    private final static float sec = (float)5;
    public boolean end_game=false;
    private State root,actual;


    public void verify_end_game(State s){
        if (actual.layout.winningVerification() != -2) {
            if(actual.layout.your_simbol(s.pc)=='O' && actual.layout.winningVerification()==s.layout.win)
                System.out.println("win pc1");
            else if(actual.layout.your_simbol(s.pc)=='X' && actual.layout.winningVerification()==s.layout.win)
                System.out.println("win pc2");
            else 
                System.out.println("Draw");
            end_game=true;
        }
    }
    final public State mcts(State s) throws CloneNotSupportedException { // algoritmo bfs
        actual = s;
        root=s.father;
        float end = sec * 100000000, i = 0;
        while (i < end) {
            float in = System.nanoTime();
            if(!actual.childs.isEmpty())
                actual=selection(s);
            //System.out.println("pass sel");
            actual.childs=sucessores(actual);
            //System.out.println("pass exp");
            simulation(actual);
            actual=s;
           // System.out.println("pass sim");
            i += (System.nanoTime() - in);
        }
        double max=-Double.MAX_VALUE;
        for (State suc : s.childs) {
            if(suc.n>max){
                actual=suc;
                max=actual.n;
            }
            //System.out.println(suc.n+" "+suc.w+" "+suc.father.n+" "+suc.uct()+" "+suc.pc);
            //System.out.println(suc);
        }
        // System.out.println(actual);
        //System.out.println(actual.layout.your_simbol(s.pc));
        verify_end_game(actual);
        return actual;
    }


    public State selection(State s) {
        State actual=s;
        double max=-Double.MAX_VALUE;
        if (actual.pc % 2 != 0)
            max = Double.MAX_VALUE;
        while (!actual.childs.isEmpty()) {
            for (State suc : actual.childs) {
                if (suc.uct() >= max && actual.pc % 2 == 0) {
                    max = suc.uct();
                    actual = suc;
                } else if (suc.uct() <= max && actual.pc % 2 != 0) {
                    max = suc.uct();
                    actual = suc;
                }
                //System.out.println("suc:"+suc.uct());
            }
            //System.out.println("final:"+actual.uct()+" "+actual.pc);
            if (actual.pc % 2 != 0)
                max = Double.MAX_VALUE;
            else
                max = -Double.MAX_VALUE;
        }
        return actual;
    }

    public void simulation(State actual) throws CloneNotSupportedException {
        for (State suc : actual.childs) {
            State s = suc;
            while (s.layout.winningVerification() == -2) {
                List<State> sucs = sucessores(s);
                int rnd = (int) (sucs.size() * Math.random());
                s = sucs.get(rnd);
            }
            //System.out.println(suc.layout.your_simbol(suc.pc)+" "+s.layout.winningVerification()+" "+s.layout.your_simbol(s.pc));
            //System.out.println(s);
            back(suc, s.layout.winningVerification(), 1);
        }
    }
    
    public void back(State s, int w, int n) {
        State actual = s;
        actual.score(w);
        actual.n+=n;
        w=-w;
        while (actual.father != root) {
            actual.father.score(w);
            actual.father.n += n;
            w=-w;
            actual = actual.father;
        }
    }
}