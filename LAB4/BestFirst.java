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
    private BestFirst.State root;

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
        root=s.father;
        int i = 0;
        int lvl=20;
        /*int childs=sucessores(actual).size();
        int res=childs;
        for(int j=childs-1;j>=1;j++){
            res+=childs;
            int h=j;
            while(h<childs){
                res*=h;
                h++;
            }
        }*/
        for(State suc:s.father.childs){
            if(suc.equals(s)){
                actual=suc;
                break;
            }
        }
        while (i < lvl) {
            if (!actual.childs.isEmpty())
                selection(actual,actual.pc,Double.MIN_VALUE);
            //System.out.println(actual);
            expand(actual);
            //System.out.println(actual.childs);
            actual=simulation(actual);
            //System.out.println(actual.n+" "+actual.w);
            back(actual);
           // System.out.println(actual);
            i++;
        }
        //System.out.println(actual);
        double max = Double.MIN_VALUE;
        for (State suc : actual.childs) {
            if (suc.ucb() > max) {
                max = suc.ucb();
                actual = suc;
            }
            System.out.println(suc.n+" "+suc.w+" "+actual.n+" "+suc.ucb()+" "+suc.layout.your_simbol(suc.pc)+" "+suc.pc+" "+s.pc);
            System.out.println(suc);
        }
        return actual;
    }

    private void back(State s) {
        while (actual.father != root) {
            actual.father.w += actual.w;
            actual.father.n += actual.n;
            actual = actual.father;
            //System.out.println(actual);
        }
    }

    private void selection(State s,int pc,double max) {
        for (State suc : s.childs) {
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
        //System.out.println(s.pc);
        actual.childs = sucessores(s);
        //System.out.println(actual.childs);
    }

    private State simulation(State sim) throws CloneNotSupportedException {
        for (State suc : sim.childs) {
            State s=suc;
            while (s.layout.winningVerification()==-1) {
                List<State> sucs = sucessores(s);
                int rnd = (int) (sucs.size() * Math.random());
                s = sucs.get(rnd);
                //System.out.println(s);
                //System.out.println(s.layout.winningVerification());
            }
            suc.w+=s.layout.winningVerification();
            suc.n+=1;
            sim.w+=suc.w;
            sim.n+=1;
            System.out.println(suc);
            System.out.println(suc.w+" "+suc.n);
            //System.out.println(s);
        }
    return sim;
    }
}