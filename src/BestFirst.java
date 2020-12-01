import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class BestFirst {
    static class State {
        private  Ilayout layout,goal;
        private  State father;
        private List<State> childs=new ArrayList<>();
        private double n=0,w=0,g,f,h;
        private static final double c=Math.sqrt(2);

        public State( Ilayout l,  State n,Ilayout goal) throws CloneNotSupportedException {
            layout = l;
            father = n;
            this.goal=goal;
            if(n==null){
                g=0;
            }else{
                g=father.g+l.getG();
            }
            h=layout.getH(goal);

        }

        public String toString() {
            return layout.toString();
        }

        public boolean isGoal(){
            return layout.isGoal(goal);
        }

        private double uct() {
            if(n==0) return Double.MAX_VALUE;
            return (double)((w/n)+c*Math.sqrt(Math.log(father.n)/n));
        }



        public State BestUCT(){
            List<State> k=childs;
            return Collections.max(k, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if (z1.uct() > z2.uct())
                        return 1;
                    if (z1.uct() < z2.uct())
                        return -1;
                    return 0;
                }});
        }

        public Board board(){
            return (Board)layout;
        }

		public int getG() {
			return (int) g;
		}

    }
    private State actual;
    private Ilayout obj;

    final private List<State> sucessores( State n) throws CloneNotSupportedException { //listar os filhos que interessam
         List<State> sucs = new ArrayList<>();
         List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n, obj);
                sucs.add(nn);
            }
        }
        return sucs;
    }


    final public Iterator<State> mcts(Ilayout s,Ilayout s2) throws CloneNotSupportedException { // algoritmo bfs
        actual=new State(s,null,s2);
        while(!actual.isGoal()){
            if(!actual.childs.isEmpty()){
                actual=selection(actual);
            }
            System.out.println(actual);
            if(actual.isGoal()){
                break;
            }
            actual.childs=sucessores(actual);
            actual=simulation(actual);
        }

        List<State> sol=new ArrayList<State>();
        while(actual!=null){
            sol.add(actual);
            actual=actual.father;
        }
        Collections.reverse(sol);
        return sol.iterator();
        
        //return (Board)actual.layout;
    }


    private State back(State s) throws CloneNotSupportedException {
        /*for(State suc:s.childs){
            System.out.println(suc+"\n"+suc.n);
        }*/
        State res=Collections.max(s.childs, new Comparator<State>() {
            @Override
            public int compare(State z1, State z2) {
                if ((z1.n) > (z2.n)){
                        return 1;
                }
                if (z1.n < z2.n)
                    return -1;
                return 0;
            }
        });
        return res;
    }

    private State simulation(State s) throws CloneNotSupportedException {
        actual=s;
        int w=0;
        for(State suc:s.childs){
            s=suc;
            while(s.g<=actual.h || !s.isGoal()){
                List<State> sucs=sucessores(s);
                int rn=(int)(Math.random()%sucs.size());
                s=sucs.get(rn);
            }
            if(s.isGoal()) w=1;
            actual=backpropagation(s,w,1);
        }
        if(actual.isGoal()){
            w=1;
        }
            actual=backpropagation(s,w,1);
        return actual;
    }

    private State backpropagation(State actual2, int w, int i) {
        while(actual2.father!=null){
            actual2.w+=w;
            actual2.n+=i;
            actual2=actual2.father;
        }
        actual2.w+=w;
        actual2.n+=i;
        return actual2;
    }

    private State selection(State s) {
        while(!s.childs.isEmpty()){
            s=s.BestUCT();
        }
        return s;
    }
}