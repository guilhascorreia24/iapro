import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class MCTS {
    static class State {
        private Ilayout layout;
        public State father;
        public List<State> childs = new ArrayList<>();
        public double s, w, l,uct;
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
            if (x >= 0)
                w += 1;
            else if(x<0){
                l+=1;
            }
        }   

        public boolean isfinalnode() {
            return final_node;
        }

        public String toString() {
            return layout.toString();
        }

        public double uct(boolean t) {
            double c = Math.sqrt(2);
            if (t) {
                if (s == 0)
                    return (double) max;
                uct = w / s + c * Math.sqrt(Math.log(father.s) / s);
            } else {
                // c=-c;
                if (s == 0)
                    return (double) -max;
                uct = l/ s + c * Math.sqrt(Math.log(father.s) / s);
            }
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
                    if (z1.uct(true) > z2.uct(true))
                        return 1;
                    if (z1.uct(true) < z2.uct(true))
                        return -1;
                    return 0;
                }
            });
        }

        public boolean childsSearched(){
            for(State c:childs){
                if(c.s==0){
                    return true;
                }
            }
            return false;
        }

        public State WorstUCT() {
            return Collections.min(childs, new Comparator<State>() {   // ver o uct menor dos filhos com visitas abaixo da media 
                @Override
                public int compare(State z1, State z2) {
                    if (z1.uct(false) > z2.uct(false))
                        return 1;
                    if (z1.uct(false) < z2.uct(false))
                        return -1;
                    return 0;
                }
            });
        }

    }

    private State actual, root;
    public boolean end_game = false;
	public Board BestNextMove(Board b) throws CloneNotSupportedException {
        if(actual==null){
            actual=new State(b,null);
            
        }
        int i=0,limit=1000;
        while(i<limit){
            if(!actual.childs.isEmpty()){
                actual=selection(actual);
            }
            if(!actual.final_node){
                expand(actual);
                simulation(actual);
            }
        }
        return (Board)actual.layout;
	}

    private void simulation(MCTS.State actual2) {
        for(State s:actual2.childs){
            int deepth=10;
            s.w=minimax_heuristic(s,deepth,true,-Integer.MAX_VALUE,Integer.MAX_VALUE);
        }
    }

    private void minimax_heuristic(MCTS.State s,int deep,boolean max,int a,int b) {
        
    }   

    private void expand(MCTS.State n) throws CloneNotSupportedException {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            State nn = new State(e, n);
            sucs.add(nn);
        }
        n.childs=sucs;
    }

    private MCTS.State selection(MCTS.State actual2) {
        boolean max=true;
        while(!actual2.final_node){
            if(max){
                actual2=actual2.BestUCT();
                max=!max;
            }else{
                actual2=actual2.WorstUCT();
                max=!max;
            }
        }
        return actual;
    }


    
}