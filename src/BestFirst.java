import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class BestFirst {
    static class State {
        private  Ilayout layout;
        private  State father;
        private List<State> childs=new ArrayList<>();
        private double n=0,w=0,l=0;
        private boolean final_node=false;
        private static final double c=Math.sqrt(2);
        private int max;
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
            if(n==0) return max;
            return (double)((w/n)+c*Math.sqrt(Math.log(father.n)/n));
        }

        public State BestUCT(){
            max=Integer.MAX_VALUE;
            return Collections.max(childs, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if (z1.uct() > z2.uct())
                        return 1;
                    if (z1.uct() < z2.uct())
                        return -1;
                    return 0;
                }});
        }

        public State WorstUCT(){
            max=Integer.MIN_VALUE;
            return Collections.min(childs, new Comparator<State>() {
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
        int playouts=0,limit=50;//1000
        while(playouts<limit){
            if(!actual.childs.isEmpty()){
                actual=selection(actual);
            }
            //System.out.println(actual);
            if(!actual.final_node)
                actual.childs=sucessores(actual);
            actual=simulation(actual);
            playouts++;
        }
        if(!root.final_node)
            actual=bestmove(root);
        if(actual.final_node){
            end_game=true;
            if(actual.layout.verifywinner()!=0){
                if(i%2==0) winner="PC2";
                else winner="PC1";
            }else{
                winner="draw";
            }
        }
        return (Board)actual.layout;
    }

    private State bestmove(State s) throws CloneNotSupportedException {
        for(State suc:s.childs){
            System.out.println("sim: "+suc.n+" win:"+suc.w+" uct: "+suc.uct()+"\n"+suc);
            for(State suc_suc:suc.childs){
                //System.out.println("sim: "+suc_suc.n+"\n"+suc_suc);
            }
        }
        State res=Collections.max(s.childs, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if(z1.n==z2.n && z1.l<z2.l)
                        return 1;
                    if(z1.n==z2.n && z1.l>z2.l)
                        return -1;
                    if (z1.n > z2.n)
                        return 1;
                    if (z1.n < z2.n)
                        return -1;
                    return 0;
                }
            });
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

    private int score(int x){
        if(x>0){
            return 1;
        }else if(x==0){
            return 0;
        }else{
            return -1;
        }
    }

    private State backpropagation(State actual2, int w, int ii) {
        int score=score(w);
        while(actual2.father!=null){
            actual2.w+=score;
            actual2.n+=ii;
            actual2=actual2.father;
            w=-w;
            score=score(w);
        }
        actual2.w+=w;
        if(w<0) actual2.l++;
        actual2.n+=ii;
        return actual2;
    }

    private State selection(State s) {
        int p=0;
        while(!s.childs.isEmpty()){
            /*for(State suc:s.childs){
                System.out.println("uct: "+suc.uct()+"\n"+suc);
            }*/
            if(p%2==0)
                s=s.BestUCT();
            else{
                s=s.WorstUCT();
            }
            //s=s.BestUCT();
        }
        return s;
    }
}