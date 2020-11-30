import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class BestFirst {
    static class State {
        private  Ilayout layout;
        private  State father;
        private List<State> childs=new ArrayList<>();
        private double n=0,w=0;
        private boolean final_node=false;
        private static final double c=Math.sqrt(2);

        public State( Ilayout l,  State n) {
            layout = l;
            father = n;
            if(layout.verifywinner()!=-2){
                final_node= true;
            }
        }

        public String toString() {
            return layout.toString();
        }

        private double uct() {
            if(n==0) return Double.MAX_VALUE;
            return (double)((w/n)+c*Math.sqrt(Math.log(father.n)/n));
        }


        public State BestUCT(){
            List<State> k=childs;
            Collections.sort(k, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if (z1.uct() > z2.uct())
                        return 1;
                    if (z1.uct() < z2.uct())
                        return -1;
                    return 0;
                }

            });
            //System.out.println(k);
            return k.get(k.size()-1);
        }

        public Board board(){
            return (Board)layout;
        }

    }
    private State actual,root;
    public boolean end_game=false;

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

    final public Board BestNextMove(Ilayout s) throws CloneNotSupportedException { // algoritmo bfs
        actual=new State(s,null);
        State root=actual;
        //s.childs=sucessores(s);
        //System.out.println(s.father.father==null);
        int playouts=0,limit=3000;
        while(playouts<limit){
            //System.out.println(actual.childs);
            if(!actual.childs.isEmpty()){
                actual=selection(actual);
            }
            //System.out.println("sel");
           //System.out.println(actual+" "+actual.final_node);
            if(!actual.final_node)
                actual.childs=sucessores(actual);
            //System.out.println("exp");
            actual=simulation(actual);
            //System.out.println("sim");
            playouts++;
        }
        if(!root.final_node)
            actual=bestmove(root);
        else
            end_game=true;
        return (Board)actual.layout;
    }

    private State bestmove(State s) {
        List<State> k=s.childs;
        Collections.sort(k, new Comparator<State>() {
            @Override
            public int compare(State z1, State z2) {
                if (z1.n > z2.n)
                    return 1;
                if (z1.n < z2.n)
                    return -1;
                return 0;
            }
        });
        
        for(State p:k){
            System.out.println(p);
            System.out.println(p.n);
        }
        State res=k.get(k.size()-1);
        if(res.final_node) end_game=true;
        return res;
    }

    private State simulation(State s) throws CloneNotSupportedException {
        actual=s;
        int w=s.layout.verifywinner();
        //System.out.println("ini\n"+s.father);
        for(State suc:s.childs){
            s=suc;
            while(!s.final_node){
                List<State> sucs=sucessores(s);
                int rn=(int)(Math.random()%sucs.size());
                s=sucs.get(rn);
                //System.out.println(s.final_node);
            }
            w=s.layout.verifywinner();
            actual=backpropagation(s,w,1);
            //System.out.println("fim\n"+actual);
        }
        if(actual.final_node)
            actual=backpropagation(s,w,1);
        return actual;
    }

    private State backpropagation(State actual2, int w, int i) {
        while(actual2.father!=null){
            if(w>0)
                actual2.w+=w;
            actual2.n+=i;
            //System.out.println(actual2);
            actual2=actual2.father;
            w=-w;
        }
        if(w>0)
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