import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

class BestFirst {
    static class State {
        private  Ilayout layout;
        private  State father;
        private List<State> childs=new ArrayList<>();
        private double n=0,w=0,l=0,d=0;
        private boolean final_node=false;
        private static final double c=Math.sqrt(2);
        private int max=Integer.MAX_VALUE;

        public State( Ilayout l,  State n) {
            layout = l;
            father = n;
            if(layout.verifywinner()!=-2){
                final_node= true;
            }
        }

        public State getFather(){
            return father;
        }
        public void setSim(int n){
            this.n=n;
        }

        public void setWin(int w){
            this.w=w;
        }


        public boolean isfinalnode(){
            return final_node;
        }

        public String toString() {
            return layout.toString();
        }

        public double uct() {
            if(n==0) return max;
            return (w/n)+c*Math.sqrt(Math.log(father.n)/n);
        }

        public boolean equals(Object b){
            return layout.equals((Ilayout)b); 
        }

        public State BestUCT(){
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

    }
    private State actual,root_father;
    public boolean end_game=false;

    final private List<State> sucessores( State n) throws CloneNotSupportedException { //listar os filhos que interessam
         List<State> sucs = new ArrayList<>();
         List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
                State nn = new State(e, n);
                sucs.add(nn);
        }
        return sucs;
    }


    final public Board BestNextMove(Ilayout s) throws CloneNotSupportedException { // algoritmo bfs
        if(actual!=null){
            if(!actual.layout.equals(s)){
                for(State suc:actual.childs){
                    if(suc.layout.equals(s)){
                        actual=suc;
                        break;
                    }
                }    
            }
        }else{
            actual=new State(s,null);
        }
        //actual=new State(s,null);
        State root=actual;
        root_father=actual.father;
        //System.out.println(actual.n+" "+actual.childs.isEmpty());
        root_father=actual.father;
        int playouts=0,limit=5;//1000
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
        //System.out.println(root.n);
        if(!root.final_node)
            actual=bestmove(root);
        if(actual.final_node) end_game=true;
        return (Board)actual.layout;
    }

    private State bestmove(State s) throws CloneNotSupportedException {
        /*List<State> l=new ArrayList<>(s.childs);
        Collections.sort(l, new Comparator<State>() {
            @Override
            public int compare(State z1, State z2) {
                if(z1.n>z2.n)
                    return 1;
                if(z1.n<z2.n)
                    return -1;
                return 0;
            }
        });
        for(State suc:l){
            System.out.println("sim:"+suc.n+" wins:"+suc.w+"\n"+suc);
        }*/
        State res=Collections.max(s.childs, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if(z1.n>z2.n)
                        return 1;
                    if(z1.n<z2.n)
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
               // System.out.println((int)Math.random()*sucs.size());
                int rn=(int)(new Random().nextInt(sucs.size()));
                //System.out.println(rn);
                s=sucs.get(rn);
            }
            w=s.layout.verifywinner();
            actual=backpropagation(s,w,1);
        }
        if(actual.final_node)
            actual=backpropagation(s,w,1);
        return actual;
    }

    private double score(int x){
        if(x>0){
            return 1;
        }else if(x==0){
            return 0.5;
        }else{
            return 0;
        }
    }

    private State backpropagation(State actual2, int w, int ii) {
        double score=score(w);
        while(actual2.father!=root_father){
            actual2.w+=score;
            //if(w<0) actual2.l++;
            //else if(w==0) actual2.d++;
            actual2.n+=ii;
            actual2=actual2.father;
            w=-w;
            score=score(w);
        }
        //actual2.w+=w;
        //if(w<0) actual2.l++;
        //else if(w==0) actual2.d++;
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