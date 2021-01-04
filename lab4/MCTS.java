import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

class MCTS { // https://nestedsoftware.com/2019/08/07/tic-tac-toe-with-mcts-2h5k.152104.html
    public static double c = 1.41, limit = 600;

    static class State {
        public Ilayout layout;
        public State father;
        public List<State> childs = new ArrayList<>();
        public double simulations, wins, draws;
        public boolean final_node = false;

        /**
         * Cria um estado
         * 
         * @param l Ilayout, Representacao do estado
         * @param n State, estado pai
         */
        public State(Ilayout p, State n) {
            layout = p;
            father = n;
            simulations = 0.0;
            wins = 0;
            draws = 0;
            if (layout.stateBoard() != -2) {
                final_node = true;
            }
        }

        /**
         * atualiza o valor das vitorias,
         * 
         * @param score 1 (vitoria),0.5 (empate) e 0 (derrota)
         */
        private void setWin(double score) {
            if (score == 1)
                this.wins += score;
            else if (score == 0.5)
                this.draws += score;
        }

        /**
         * Imprime o estado
         */
        public String toString() {
            if (!childs.isEmpty()) {
                State res = Collections.max(childs, new Comparator<State>() {
                    @Override
                    public int compare(State z1, State z2) {
                        if (z1.simulations > z2.simulations)
                            return 1;
                        if (z1.simulations < z2.simulations)
                            return -1;
                        return 0;
                    }
                });
                StringWriter writer = new StringWriter();
                PrintWriter pw = new PrintWriter(writer);
                pw.println(res.layout.getplayer() + " move[" + res.layout.getPosition() + "]");
                return layout.toString() + writer.toString();
            }
            return layout.toString();
        }

        /**
         * Calcula o uct do estado
         * 
         * @return double valor do uct
         */
        public double uct() {
            if (simulations == 0 || final_node)
                return Integer.MAX_VALUE;
            if (wins < 0 || simulations < 0 || (father != null && father.simulations < 1)) {
                throw new IllegalArgumentException("wins negative");
            }
            return ((wins + draws) / simulations) + c * Math.sqrt(Math.log(father.simulations) / simulations);
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
         * Econtra o filho com maior UCT, estando na posiÃ§ao de oponente (Identico ao
         * bestUCT)
         * 
         * @return State o filho com menor UCT
         */
        public State WorstUCT() {
             
            State res = Collections.max(childs, new Comparator<State>() {
                @Override
                public int compare(State z1, State z2) {
                    if (z1.uct() > z2.uct())
                        return 1;
                    if (z1.uct() < z2.uct())
                        return -1;
                    return 0;
                }
            });
            return res;
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
        // l.add(k);
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
        while (root.simulations < limit) {
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
                if (z1.simulations > z2.simulations)
                    return 1;
                if (z1.simulations < z2.simulations)
                    return -1;
                return 0;
            }
        });
        return res;
    }

    /**
     * Realiza simulacoes, faz uma jogada para ganhar, caso seja direta (simulaÃ§ao
     * informada) para cada filho e faz backpropagation quando encontrar um final
     * node, caso contrario faz so backpropagation
     * 
     * @param s State, estado encontrado na fase de selecao
     * @return State estado do jogo em se encontra
     * @throws CloneNotSupportedException
     */
    public State simulation(State s) throws CloneNotSupportedException {
        State actual2 = s;
        double w =actual2.layout.stateBoard();
        //double w=root.childs.get(0).layout.verifywinner(actual2.layout);
        if (!actual.final_node) {
            for (State suc : s.childs) {
                s = suc;
                while (!s.final_node) {
                    List<State> sucs = expand(s);
                    State p = null;
                    for (State t : sucs) {
                        if (t.final_node) {
                            p = t;
                            break;
                        }
                    }
                    if (p == null) {
                        int rn = (int) (new Random().nextInt(sucs.size()));
                        p = sucs.get(rn);
                    }
                    s = p;
                }
                // w = root.childs.get(0).layout.verifywinner(s.layout);
                w = suc.layout.verifywinner(s.layout);
                actual = backpropagation(suc, w);
            }

        } else {
            actual = backpropagation(actual2, w);
        }
        return actual;
    }

    /**
     * Devolve +1 simulacao e o resultado da simulacao ate ao estado do jogo atual
     * 
     * @param actual2 State filho do estado que Ã© selecionado para a simulacao
     * @param w       double, resultado da simulacao (1 vitoria,0.5 empate,0
     *                derrota)
     * @return State estado do jogo atual
     */
    public State backpropagation(State actual2, double w) {
        while (actual2.father != null) {
            actual2.setWin(w);
            actual2.simulations += 1;
            actual2 = actual2.father;
            if (w != Ilayout.DRAW) {
                w = (w + 1) % 2;
            }
        }
        actual2.setWin(w);
        actual2.simulations += 1;
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
        while (!s.childs.isEmpty()) {
            s = s.BestUCT();
        }
        return s;
    }
}
