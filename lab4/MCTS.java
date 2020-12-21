import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class MCTS {
    public static double c = 0.185353;

    static class State {
        public Ilayout layout;
        public State father;
        public List<State> childs = new ArrayList<>();
        public double s, w;
        public boolean final_node = false;
        private int max = Integer.MAX_VALUE;
        private int g;

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
            if (layout.stateBoard() != -2) {
                final_node = true;
            }
            if(father==null) g=0;
            else g=father.g+1;
        }

        /**
         * atualiza o valor das vitorias
         * 
         * @param score 1 (vitoria) ,0.5 empate e 0 derrota
         */
        private void setWin(double score) {
            if(score>0)
                this.w += score;

        }

        /**
         * Imprime o estado
         */
        public String toString() {
            return layout.toString();
        }

        /**
         * Calcula o uct do estado
         * 
         * @return double valor do uct
         */
        public double uct() {
            // System.out.println(w+" "+s);
            if (s == 0)
                return max;
            if (w < 0 || s < 0 || (father != null && father.s < 1)) {
                throw new IllegalArgumentException("wins negative");
            }
            return (w / s) + c * Math.sqrt(Math.log(father.s) / s);
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
    final public List<State> expand(State n) throws CloneNotSupportedException { // listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            State nn = new State(e, n);
            sucs.add(nn);
        }
        return sucs;
    }

    final public Iterator<State> solve(Ilayout s) throws CloneNotSupportedException {
        List<State> l = new ArrayList<>();
        State k = new State(s, null);
        l.add(k);
        while (!end_game) {
            k = BestNextMove(k.layout);
            l.add(k);
        }
        return l.iterator();
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
        double limit = 500000;// 50000
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
        /*for (State s1 : s.childs) {
            System.out.println(s1.uct() + " " + s1.w/s1.s + " " + s1.s + "\n" + s1);
            boolean max=true;
            while (!s1.childs.isEmpty()) {
                if (max) {
                    s1 = s1.BestUCT();
                    max = false;
                } else {
                    s1 = s1.WorstUCT();
                    max = true;
                }
                System.out.println(s1+"\n");
            }
        }
        System.out.println("----------------------------------");*/
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
     * Realiza simulacoes para cada filho e faz backpropagation caso nao seja final
     * node, caso contrario faz so backpropagation
     * 
     * @param s State, estado encontrado na fase de selecao
     * @return State estado do jogo em se encontra
     * @throws CloneNotSupportedException
     */
    public State simulation(State s) throws CloneNotSupportedException {
        actual = s;
        double w = root.childs.get(0).layout.verifywinner(actual.layout);
        if (!actual.final_node) {
            for (State suc : s.childs) {
                s = suc;
                while (!s.final_node) {
                    List<State> sucs = expand(s);
                    int rn = (int) (new Random().nextInt(sucs.size()));
                    s = sucs.get(rn);
                }
                w = root.childs.get(0).layout.verifywinner(s.layout);

                actual = backpropagation(suc, w);
            }
        } else
            actual = backpropagation(actual, w);
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
    private State selection(State s) {
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