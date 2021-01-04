import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

class MCTS {
    public static double c =0.4/*Math.sqrt(1/30.8005)*/, limit = 650;

    static class State {
        public Ilayout layout;
        public State father;
        private int childs_wins;
        public List<State> childs = new ArrayList<>();
        public double s, w;
        public boolean final_node = false;
        private int max = -Integer.MAX_VALUE;
        public int g;

        /**
         * Cria um estado
         * 
         * @param l Ilayout, Representacao do estado
         * @param n State, estado pai
         */
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            s = 0.0;
            w = 0;
            childs_wins=0;
            if (layout.stateBoard() != -2) {
                final_node = true;
            }
            if (father == null)
                g = 0;
            else{
                g = father.g + 1;
                max=-n.max;}
        }

        /**
         * atualiza o valor das vitorias,
         * 
         * @param score 1 (vitoria),0.5 (empate) e 0 (derrota)
         */
        private void setWin(double score) {
            this.w += score;
        }

        /**
         * Imprime o estado
         */
        public String toString() {
            if (!childs.isEmpty()) {
                State res=Collections.max(childs, new Comparator<State>() {
                    @Override
                    public int compare(State z1, State z2) {
                        if (z1.s > z2.s)
                            return 1;
                        if (z1.s < z2.s)
                            return -1;
                        return 0;
                    }
                });
                for(State r:father.childs){
                   System.out.println(r.uct()+" "+r.w+" "+r.s+" "+r.childs_wins);
                   System.out.println(r.layout);
                   /*for(State r1:r.childs){
                    System.out.println(r1.uct()+" "+r1.w+" "+r1.s+" "+r1.childs_wins);
                    System.out.println(r1.layout);
                 }*/
                }
                StringWriter writer = new StringWriter();
                PrintWriter pw = new PrintWriter(writer);
                pw.println(res.layout.getplayer() + " move[" + res.layout.getPosition() + "]");
                return layout.toString()+writer.toString();
            }
            return layout.toString();
        }

        /**
         * Calcula o uct do estado
         * 
         * @return double valor do uct
         */
        public double uct() {
            // System.out.println(w+" "+s);
            if (s == 0 || final_node)
                return max;
            if (w < 0 || s < 0 || (father != null && father.s < 1)) {
                throw new IllegalArgumentException("wins negative");
            }
            return ((w)/ s) + c * Math.sqrt(Math.log(father.s) / s);
        }

        @Override
        public boolean equals(Object b) {
            State s = (State) b;
            return layout.equals(s.layout);
        }

        /**
         * Encontra o filho com maior valor UCT
         * 
         * @return State o filho com maior uct
         */
        public State BestUCT() {
            return Collections.max(childs, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if (z1.uct() > z2.uct())
                        return 1;
                    if (z1.uct() < z2.uct())
                        return -1;
                    return 0;
                }
            });
        }

        /**
         * Econtra o filho com menor valor UCT
         * 
         * @return State o filho com menor UCT
         */
        public State WorstUCT() {
            return Collections.min(childs, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if (z1.uct() > z2.uct())
                        return 1;
                    if (z1.uct() < z2.uct())
                        return -1;
                    return 0;
                }
            });
        }

    }

    public State actual, root;
    public boolean end_game = false;

    /**
     * Encontra os filhos de um estado
     * 
     * @param n State estado pai
     * @return List<State> filhos do estado n
     * @throws CloneNotSupportedException
     */
    protected List<State> expand(State n) throws CloneNotSupportedException { // listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            State nn = new State(e, n);
            sucs.add(nn);
        }
        return sucs;
    }

    final public List<State> solve(Ilayout s) throws CloneNotSupportedException {
        List<State> l = new ArrayList<>();
        actual = new State(s, null);
        //l.add(k);
        while (!end_game) {
            actual = BestNextMove(actual.layout);
           // System.out.println(actual);
            l.add(actual);
        }
        return l;
    }

    /**
     * Utiliza o algoritmo MCTS para encntra o melhor movimento a realizar
     * 
     * @param s Ilayout, representacao do estado do jogo atual
     * @return Melhor movimento a realizar
     * @throws CloneNotSupportedException
     */
    final public State BestNextMove(Ilayout s) throws CloneNotSupportedException { // algoritmo mcts
        actual = new State(s, null);
        if (actual.final_node) {
            end_game = true;
            return actual;
        }
        root = actual;
        while (root.s < limit) {
            if (!actual.childs.isEmpty()) {
                actual = selection(actual);
            }
            if (!actual.final_node)
                actual.childs = expand(actual);
            actual = simulation(actual);
        }
        actual = bestmove(root);
        if (actual.final_node)
            end_game = true;
        return actual;
    }

    /**
     * Escolhe o filho que tiver mais visitas
     * 
     * @param s State, estado em que se encntra o jogo
     * @return State, estado com mais visitas
     * @throws CloneNotSupportedException
     */
    private State bestmove(State s) throws CloneNotSupportedException {
        State res = Collections.max(s.childs, new Comparator<State>() {
            @Override
            public int compare(State z1, State z2) {
                if (z1.s > z2.s)
                    return 1;
                if (z1.s < z2.s)
                    return -1;
                return 0;
            }
        });
        return res;
    }

    /**
     * Realiza simulacoes, faz uma jogada para ganhar, caso seja direta 
     * (simulaçao informada) para cada filho e faz backpropagation quando encontrar um final
     * node, caso contrario faz so backpropagation
     * 
     * @param s State, estado encontrado na fase de selecao
     * @return State estado do jogo em se encontra
     * @throws CloneNotSupportedException
     */
    public State simulation(State s) throws CloneNotSupportedException {
        State actual2 = s;
        double w = root.childs.get(0).layout.verifywinner(actual2.layout);
        if (!actual.final_node) {
            for (State suc : s.childs) {
                s = suc;
                while (!s.final_node) {
                    List<State> sucs = expand(s);
                    State p=null;
                    for(State t:sucs){
                        if(t.final_node){
                            p=t;
                            break;
                        }else{
                        for(State t1:expand(t)){
                            if(t1.final_node){
                                p=t1;
                                break;
                            }
                            if(suc.layout.verifywinner(t1.layout)==1){
                                p=t1;
                                break;
                            }
                        }
                        if(p!=null) break;
                    }
                    }
                    if(p==null){
                        int rn = (int) (new Random().nextInt(sucs.size()));
                        p = sucs.get(rn);
                    }
                    s=p;
                }
                w = root.childs.get(0).layout.verifywinner(s.layout);
                if(suc.g+1==s.g){
                    suc.childs_wins++;
                }
                actual = backpropagation(suc, w);
            }
            Collections.sort(actual2.childs,new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if (z1.childs_wins < z2.childs_wins)
                        return -1;
                    if (z1.childs_wins > z2.childs_wins)
                        return 1;
                    return 0;
                }
            });
        } else{
            actual = backpropagation(actual2, w);
        }
        return actual;
    }

    /**
     * Devolve +1 simulacao e o resultado da simulacao ate ao estado do jogo atual
     * 
     * @param actual2 State filho do estado que é selecionado para a simulacao
     * @param w       double, resultado da simulacao (1 vitoria,0.5 empate,0
     *                derrota)
     * @return State estado do jogo atual
     */
    public State backpropagation(State actual2, double w) {
        while (actual2.father != null) {
            actual2.setWin(w);
            actual2.s += 1;
            actual2 = actual2.father;
        }
        actual2.setWin(w);
        actual2.s += 1;
        return actual2;
    }

    /**
     * Seleciona o estado, utilizado o minimax e para sua escolha usa o valor do UCT
     * para decidir, termina quanto um estado nao tiver filhos
     * 
     * @param s State estado atual do jogo
     * @return State devolve um estado sem filhos
     */
    public State selection(State s) {
        boolean max = true;
        while (!s.childs.isEmpty()) {
            if (max) {
                s = s.BestUCT();
                max = false;
            } else {
                s = s.WorstUCT();
                max = true;
            }
        }
        return s;
    }
}