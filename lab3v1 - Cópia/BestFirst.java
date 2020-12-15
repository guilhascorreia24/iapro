
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;



class BestFirst {
    static class State {
        private Ilayout layout;
        private State father;
        private List<State> childs = new ArrayList<>();
        private double g, h, f, s, w;
        private boolean final_node = false;

        public State(Ilayout l, State n, Ilayout obj) throws CloneNotSupportedException {
            layout = l;
            father = n;
            s=0;w=0;
            h = l.getH(obj);
            if (father != null)
                g = father.g + l.getG();
            else
                g = 0.0;
            f = g + h;
            if (l.isGoal(obj))
                final_node = true;
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        public boolean isGoal(Ilayout obj) {
            return this.layout.isGoal(obj);
        }

        public double getF() {
            return f;
        }

        public double getH() {
            return h;
        }

        private double uct() {
            if (s == 0)
                return Integer.MAX_VALUE;
            return (w / s) + 2 * Math.sqrt(Math.log(father.s) / s);
        }

        public State BestChild() {
            double t = -Integer.MAX_VALUE;
            State t1 = null;
            System.out.println("+++\n"+this);
            for (State s : childs) {
                System.out.println(s.s+" "+this.s+" "+s.w);
                if (s.uct() > t) {
                    t1 = s;
                    t = s.uct();
                }
            }
           // System.out.println();
            return t1;
        }

    }

    protected Stack<State> abertos;
    private State actual, root;
    private Ilayout objective;
    // private double max_h;

    /***
     * Gera os filhos do no atual
     * 
     * @param n estado actual
     * @return retorna a lista de filhos que o estado actual tem, expecto os que sao
     *         iguais ao estado atual
     * @throws CloneNotSupportedException caso nao consiga clonar uma configuracao
     */
    final private List<State> sucessores(State n) throws CloneNotSupportedException { // listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n, objective);
                sucs.add(nn);
            }
        }
        Collections.sort(sucs, new Comparator<State>(){
            @Override
            public int compare(State s,State s1){
                if(s.h<s1.h)return -1;
                if(s.h>s1.h)return 1;
                return 0;
            }
        });
        return sucs;
    }

    /***
     * Esta funcao, utiliza o algoritmo A*, que consiste em pesquisar pelo estado
     * com valor menor de F (g+h) que esta na lista ordenada abertos, e os casos que
     * sao avaliados vao para lista fechados para nao serem pesquisados outra vez
     * 
     * @param s,    configuracao inicial
     * @param goal, configuracao final
     * @return retorna a lista com a sequencia de passos minimos para chegar a
     *         configuracao final
     * @throws CloneNotSupportedException, caso nao consiga clonar uma configuracao
     */
    final public Iterator<State> solve(Ilayout s, Ilayout goal) throws CloneNotSupportedException { // algoritmo bfs
        actual = new State(s, null, goal);
        objective = goal;
        root = actual;
        double h = root.h;
        System.out.println(actual.h);
        while (!actual.final_node) {

            if (!actual.childs.isEmpty()) {
                actual = selection(actual);
            }
            if (actual.final_node)
                break;
            if (!actual.final_node && actual.g < h){
                actual.childs = sucessores(actual);
            }
            double v = simulation(actual);
            backpropagation(actual, v);
            //System.out.println(root);
            actual = root;
        }
        List<State> sol = new ArrayList<State>();
        while (actual != null) {
            sol.add(actual);
            actual = actual.father;
        }
        Collections.reverse(sol);
        return sol.iterator();
    }

    private void backpropagation(BestFirst.State actual2, double v) {
        while (actual2.father != null) {
            double max=0,min=Integer.MAX_VALUE;
            //System.out.println(actual2.father);
            if(actual2.father!=null ){
            for(State t:actual2.father.childs){
                if(t.w>max)max=t.w;
                if(t.w<min)min=t.w;
            }}

            if(min==0 && max==0)
                actual2.w=v;
            else
                actual2.w += (v-min)/(max-min);
            actual2.s+=(actual.g-actual2.g)+actual.s;
            actual2 = actual2.father;
        }
    }

    public State BestChildsHeuristc(List<State> l, State s) {
        double h = s.h;
        State u = s;
        for (State t : l) {
            if (t.h < h) {
                u = t;
                h = u.h;
            }
        }
        return u;
    }

    private double simulation(BestFirst.State actual2) throws CloneNotSupportedException {
        State t = actual2;
        while (actual2.g < root.h) {
            if (actual2.isGoal(objective) || (actual2.father != null && actual2.h >= actual2.father.h))
                break;
            List<State> s = sucessores(actual2);
            actual2 = BestChildsHeuristc(s, actual2);
        }
        actual = t;
        if (actual2.isGoal(objective)) {
            actual.w=1;
        }
        return actual2.w;
    }

    private BestFirst.State selection(State s) {
        while(!s.childs.isEmpty() && s.g<root.h){
            s=s.BestChild();
         //  System.out.println(s);
        }
       // System.out.println("00000");
        return s;
    }
}