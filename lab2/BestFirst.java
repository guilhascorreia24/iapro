
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

class BestFirst {
    static class State {
        private  Ilayout layout;
        private  State father;
        private double g,h,f;

        public State( Ilayout l,  State n,Ilayout obj) throws CloneNotSupportedException {
            layout = l;
            father = n;
            h=l.getH(obj);
            if (father != null)
                g = father.g + l.getG();
            else
                g = 0.0;
            f=g+h;
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        public boolean isGoal(Ilayout obj){
            return this.layout.isGoal(obj);
        }

        public double getF(){
            return f;
        }

        public double getH(){
            return h;
        }
        /*@Override
        public boolean equals( Object b){
             State b2=(State)b;
            return layout.equals(b2.layout);
        }*/
    }

    //protected Queue<State> abertos;
    private State actual;
    private Ilayout objective;
    private int max_deep;

    final private List<State> sucessores( State n) throws CloneNotSupportedException { //listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n,objective);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    final public Iterator<State> solve(Ilayout s, Ilayout goal) throws CloneNotSupportedException { // algoritmo bfs
        objective = goal;
         Queue<State> abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getF() - s2.getF()));
         List<State> fechados = new ArrayList<>();
        abertos.add(new State(s, null,objective));
        actual=abertos.element();
        //System.out.println(s.toString());
        List<State> sucs;
        while(!actual.isGoal(goal)){
            //System.out.println(actual.getH());
            if(abertos.isEmpty()){
                throw new IllegalStateException("Fail");
            }
            actual=abertos.poll();
            if(actual.layout.equals(objective)){
                break;
            }else{
                sucs=sucessores(actual);
                fechados.add(actual);
                for(State suc:sucs){
                    if(!fechados.contains(suc)){
                        abertos.add(suc);
                    }
                    if(suc.layout.equals(objective)){
                        actual=suc;
                        //System.out.println(suc);
                        break;
                    }
                    //System.out.println(suc);
                }
            }
        }
        List<State> sol=new ArrayList<State>();
        
        //actual.layout=goal;
        while(actual!=null){
            sol.add(actual);
            actual=actual.father;
        }
        Collections.reverse(sol);
        return sol.iterator();
    }

    /*final public Iterator<State> solve(Ilayout s, Ilayout goal) throws CloneNotSupportedException { // algoritmo IDS
        objective = goal;
       // Queue<State> abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getG() - s2.getG()));
        Stack<State> abertos=new Stack<>();
        List<State> fechados = new ArrayList<>();
        abertos.add(new State(s, null));
        actual=abertos.firstElement();
        List<State> sucs;
        //System.out.println(max_deep);
        if(max_deep==0){
            if(actual.isGoal(goal)){
                List<State> sol=new ArrayList<State>();
        
                //actual.layout=goal;
                while(actual!=null){
                    sol.add(actual);
                    actual=actual.father;
                }
                Collections.reverse(sol);
                return sol.iterator(); 
            } else {
                max_deep++;
                return solve(s, goal);
            }
        } else {
            while(!abertos.isEmpty()){
                actual=abertos.pop();
                if(actual.layout.equals(objective)){
                    break;
                }
                sucs=sucessores(actual);
                fechados.add(actual);
                for(State suc:sucs){
                    if(!fechados.contains(suc) && suc.getG()<max_deep){
                        abertos.push(suc);
                    }
                    if(suc.layout.equals(objective)){
                        actual=suc;
                        break;
                    }
                }
                if(actual.layout.equals(objective)){
                    break;
                }
            }
            if(actual.getG()+1==max_deep && !actual.isGoal(objective)){
                max_deep++;
                return solve(s, goal);
            }
        }
        List<State> sol=new ArrayList<State>();
        
        //actual.layout=goal;
        while(actual!=null){
            sol.add(actual);
            actual=actual.father;
        }
        Collections.reverse(sol);
        return sol.iterator(); 
    }*/
}