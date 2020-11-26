import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class BestFirst {
    static class State {
        public Ilayout layout;
        public State father;
        private List<State> childs=new ArrayList<State>();
        private final double c = Math.sqrt(1.85);
        private double w=0, n=0;
        public int pc;

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
    private final static int lvl=1000;

    final private List<State> sucessores(State n) throws CloneNotSupportedException { // listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n,n.pc+1);
                //System.out.println(nn.pc);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    final public State mcts(State s) throws CloneNotSupportedException { // algoritmo bfs
        actual = s;
        int i = 0;
        for(State suc:s.father.childs){
            if(suc.equals(s)){
                actual=suc;
                break;
            }
        }
        //System.out.println(actual.w+" "+actual.n);
        while (i < lvl) {
            if (!s.childs.isEmpty())
                selection(s,0,Double.MIN_VALUE);
            //System.out.println(actual.n+" "+actual.w);
            if(actual.father!=null)
                ///System.out.println(actual.father.n);
            //System.out.println(actual);
            expand(actual);
            //System.out.println(actual.childs.size());
            simulation();
            //System.out.println(actual.n+" "+actual.w+" "+actual.father.n);
            //System.out.println(actual);

            i++;
        }
        //System.out.println(actual);
        double max = Double.MIN_VALUE;
        for (State suc : s.childs) {
            if (suc.ucb() > max) {
                max = suc.ucb();
                actual = suc;
            }
            //System.out.println(suc.n+" "+suc.w+" "+s.n+" "+suc.ucb()+" "+suc.layout.your_simbol(suc.pc)+" "+suc.pc+" "+s.pc);
            //System.out.println(suc);
        }
        return actual;
    }

    private void back(State s,int w,int n,State f) {
        actual=s;
        while (actual.father != null) {
            actual.father.w += w;
            actual.father.n += n;
            //System.out.println(s.n+" "+s.father.n);
            actual = actual.father;
        }
    }

    private void selection(State s,int pc,double max) {
       // System.out.println(s);
        for (State suc : s.childs) {
            //System.out.println(suc);
            if (suc.ucb() > max && pc%2==0) {
                max = suc.ucb();
                actual = suc;
            }else if(suc.ucb()<max && pc%2!=0 ){
                max = suc.ucb();
                actual = suc;
            }
            //System.out.println(suc.ucb()+" "+max+" "+pc);
        }
        if (!actual.childs.isEmpty()) {
            pc++;
            if(pc%2!=0) max=Double.MAX_VALUE;
            else max=Double.MIN_VALUE;
            selection(actual,pc,max);
        }
        //System.out.println(actual);
    }

    private void expand(State s) throws CloneNotSupportedException {
        //System.out.println(s.pc);
        actual.childs = sucessores(s);
        //System.out.println(actual.childs);
    }

    private void simulation() throws CloneNotSupportedException {
        for (State suc : actual.childs) {
            State s=suc;
            while (s.layout.winningVerification()==-1) {
                List<State> sucs = sucessores(s);
                int rnd = (int) (sucs.size() * Math.random());
                s = sucs.get(rnd);
                //System.out.println(s);
                //System.out.println(s.layout.winningVerification());
            }
            back(s, s.layout.winningVerification(),1,actual.father);
        }
    }
}