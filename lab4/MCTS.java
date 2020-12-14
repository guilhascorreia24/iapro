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

    }

    private State actual, root;
    public boolean end_game = false;
	public Board BestNextMove(Board b) throws CloneNotSupportedException {


    
}