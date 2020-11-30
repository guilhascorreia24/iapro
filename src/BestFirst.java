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

    }
    private State actual;
    public boolean end_game=false;
    private int i=0;
    public String winner="";
    private int hard=23,med=10,ez=1;

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
        i++;
        actual=new State(s,null);
        State root=actual;
        int playouts=0,limit=hard;
        while(playouts<limit){
            if(!actual.childs.isEmpty()){
                actual=selection(actual);
            }
            if(!actual.final_node)
                actual.childs=sucessores(actual);
            actual=simulation(actual);
            playouts++;
        }
        if(!root.final_node)
            actual=bestmove(root);
        else{
            end_game=true;
        }
        return (Board)actual.layout;
    }

    private State bestmove(State s) throws CloneNotSupportedException {
        State res=s;
        int max=0;
        for(State suc:s.childs){
            if(suc.final_node) {
                res=suc;
                break;
            }
            List<State> sucs=sucessores(suc);
            boolean next=false;
            for(State suc_suc:sucs){
                if(suc_suc.final_node && suc_suc.layout.verifywinner()==1){
                    next=true;
                }
                //System.out.println(suc_suc+"\n"+next);
            }
            //System.out.println(suc+"\n"+suc.n);
            if(!next && suc.n>max){
                res=suc;
                max= (int) res.n;
            }
        }
        if(res.equals(s)){
            res=Collections.max(s.childs, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if (z1.n > z2.n)
                        return 1;
                    if (z1.n < z2.n)
                        return -1;
                    return 0;
                }
            });
        }

        if(res.final_node){
            end_game=true;
            if(res.layout.verifywinner()!=0){
                if(i%2==0) winner="PC2";
                else winner="PC1";
            }else{
                winner="draw";
            }
        }
        return res;
    }

    private State simulation(State s) throws CloneNotSupportedException {
        actual=s;
        int w=s.layout.verifywinner();
        for(State suc:s.childs){
            s=suc;
            while(!s.final_node){
                List<State> sucs=sucessores(s);
                int rn=(int)(Math.random()%sucs.size());
                s=sucs.get(rn);
            }
            w=s.layout.verifywinner();
            actual=backpropagation(s,w,1);
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