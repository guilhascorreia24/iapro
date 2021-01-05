/*mport java.util.List;

import org.junit.Test;

public class test {
    @Test
    public void testPrecision() throws CloneNotSupportedException {
            int i = 0, res = 0;
            while (i < 1000) {
                MCTS s = new MCTS();
                Board b = new Board("---------");
                List<MCTS.State> l = s.solve(b);
                if (l == null)
                    System.out.println("no solution was found");
                else {
                    MCTS.State t = l.get(l.size() - 1);
                    if (t.layout.stateBoard() != Ilayout.DRAW) {
                        res++;
                        //System.out.println(t);
                        
                         for (MCTS.State t1 : l) { System.out.println(t1); }
                         
                        System.out.println("--------------------");
                    }
                }
                i++;
            }
            System.out.println(res + "/" + i);
        }
}*/
