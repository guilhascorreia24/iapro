
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MCTS {
    private static double c = 1.41;
    private double limit = 1000;

    public static class State {
        private Ilayout layout;
        private State father;
        public List<State> childs = new ArrayList<>();
        public double simulations, wins;
        public boolean final_node = false;

        /**
         * Cria um estado
         * 
         * @param p Ilayout, Representacao do estado
         * @param n State, estado pai
         */
        public State(Ilayout p, State n) {
            layout = p;
            setFather(n);
            simulations = 0.0;
            wins = 0;
            if (layout.stateBoard() != -2) {
                final_node = true;
            }
        }
        /**
         * 
         * @return State,devolve o father
         */
        public State getFather() {
            return father;
        }
        /**
         * alterar o father
         * @param father state pai 
         */
        public void setFather(State father) {
            this.father = father;
        }

        /**
         * 
         * @return Ilayout, devolve o layout correspodente
         */
        public Ilayout getBoard() {
            return this.layout;
        }

        /**
         * atualiza o valor das vitorias,
         * 
         * @param score 1 (vitoria),0.5 (empate) e 0 (derrota)
         */
        private void setWin(double score) {
            this.wins += score;
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
            if (simulations == 0 || final_node)
                return Integer.MAX_VALUE;
            if (wins < 0 || simulations < 0 || (getFather() != null && getFather().simulations < 1)) {
                throw new IllegalArgumentException("wins negative");
            }
            return ((wins) / simulations) + c * Math.sqrt(Math.log(getFather().simulations) / simulations);
        }

        /**
         * compara o layout de 2 states
         * 
         */
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
    }

    public State actual, root;
    public boolean end_game = false;

    /**
     * Encontra os filhos de um estado
     * 
     * @param n State estado pai
     * @return Lista filhos do estado n
     * @throws CloneNotSupportedException devolve quando nao seja o clone
     */
    public List<State> expand(State n) throws CloneNotSupportedException { // listar os filhos que interessam
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            State nn = new State(e, n);
            sucs.add(nn);
        }
        return sucs;
    }

    /**
     * Realiza o jogo bot vs bot
     * 
     * @param s Ilayout, representacao do estado do jogo atual
     * @return Melhor movimento a realizar
     * @throws CloneNotSupportedException devolve quando nao seja o clone
     */
    final public List<State> solve(Ilayout s) throws CloneNotSupportedException {
        List<State> l = new ArrayList<>();
        actual = new State(s, null);
        while (!end_game) {
            actual = BestNextMove(actual.layout);
            l.add(actual);
        }
        return l;
    }

    /**
     * Utiliza o algoritmo MCTS para encntra o melhor movimento a realizar
     * 
     * @param s Ilayout, representacao do estado do jogo atual
     * @return Melhor movimento a realizar
     * @throws CloneNotSupportedException devolve quando nao seja o clone
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
     * 
     */
    private State bestmove(State s) {
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
     * Realiza simulacoes, faz uma jogada para ganhar, caso seja direta (simulaçao
     * informada) para cada filho e faz backpropagation quando encontrar um final
     * node, caso contrario faz so backpropagation
     * 
     * @param s State, estado encontrado na fase de selecao
     * @return State estado do jogo em se encontra
     * @throws CloneNotSupportedException devolve quando nao seja o clone
     */
    public State simulation(State s) throws CloneNotSupportedException {
        double result = s.layout.stateBoard();
        if (!actual.final_node) {
            for (State suc : s.childs) {
                State next = suc;
                while (!next.final_node) {
                    List<State> sucs = expand(next);
                    State res = null;
                    for (State suc1 : sucs) {
                        if (suc1.final_node) {
                            res = suc1;
                            break;
                        }
                    }
                    if (res == null) {
                        int rn = (int) (new Random().nextInt(sucs.size()));
                        res = sucs.get(rn);
                    }
                    next = res;
                }
                result = suc.layout.verifywinner(next.layout);
                actual = backpropagation(suc, result);
            }

        } else {
            actual = backpropagation(s, result);
        }
        return actual;
    }

    /**
     * Devolve +1 simulacao e o resultado da simulacao ate ao estado do jogo atual, 
     * de forma a que os nos Max guardam as vitorias do max e os empates, 
     * e os nos do Min guardam as vitorias do min e os empates
     * 
     * @param state State filho do estado que e selecionado para a simulacao
     * @param result_board     double, resultado da simulacao (1 vitoria,0.5 empate,0 derrota)
     * @return State estado do jogo atual
     */
    public State backpropagation(State state, double result_board) {
        while (state.getFather() != null) {
            state.setWin(result_board);
            state.simulations += 1;
            state = state.getFather();
            if (result_board != Ilayout.DRAW) {
                result_board = (result_board + 1) % 2;
            }
        }
        state.setWin(result_board);
        state.simulations += 1;
        return state;
    }

    /**
     * Seleciona o estado, utilizado o minimax e para sua escolha usa o valor do UCT
     * para decidir, termina quanto um estado nao tiver filhos
     * 
     * @param state State estado atual do jogo
     * @return State devolve um estado sem filhos
     */
    public State selection(State state) {
        while (!state.childs.isEmpty()) {
            state = state.BestUCT();
        }
        return state;
    }
}
