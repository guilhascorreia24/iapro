import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class tests {
    @Test
    public void testConstructor() {
        Board b = new Board("---------");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("---");
        pw.println("---");
        pw.println("---");
        assertEquals(writer.toString(), b.toString());
    }

    @Test
    public void testConstructor1() {
        Board b = new Board("-X--O--X-");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("-X-");
        pw.println("-O-");
        pw.println("-X-");
        assertEquals(writer.toString(), b.toString());
    }

    @Test
    public void testConstructor2() {
        Board b = new Board("XX--OO-X-");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("XX-");
        pw.println("-OO");
        pw.println("-X-");;
        assertEquals(writer.toString(), b.toString());
    }

    @Test
    public void testConstructor3() {
        Board b = new Board("XXXXOXXXX");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("XXX");
        pw.println("XOX");
        pw.println("XXX");
        assertEquals(writer.toString(), b.toString());
    }

    @Test
    public void testConstructor4() {
        Board b = new Board("OXOOOOOXO");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("OXO");
        pw.println("OOO");
        pw.println("OXO");
        assertEquals(writer.toString(), b.toString());
    }

    @Test
    public void testChildren1() throws CloneNotSupportedException {
        Board b = new Board("---------");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("X--------"));
        l.add(new Board("-X-------"));
        l.add(new Board("----X----"));
        assertEquals(l, result);
    }

    @Test
    public void testChildren2() throws CloneNotSupportedException {
        Board b = new Board("XXXXXXXXX");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        assertEquals(l, result);
    }

    @Test
    public void testChildren3() throws CloneNotSupportedException {
        Board b = new Board("XOX-XX-XO");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("XOXOXX-XO"));
        l.add(new Board("XOX-XXOXO"));
        assertEquals(l, result);
    }

    @Test
    public void testChildren4() throws CloneNotSupportedException {
        Board b = new Board("-O--X--XO");
        List<Ilayout> result = b.children();
        // System.out.println(result);
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("XO--X--XO"));
        l.add(new Board("-OX-X--XO"));
        l.add(new Board("-O-XX--XO"));
        l.add(new Board("-O--XX-XO"));
        l.add(new Board("-O--X-XXO"));
        assertEquals(l, result);
    }

    @Test
    public void testChildren5() throws CloneNotSupportedException {
        Board b = new Board("----O----");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("X---O----"));
        l.add(new Board("-X--O----"));
        assertEquals(l, result);
    }

    @Test
    public void testChildren6() throws CloneNotSupportedException {
        Board b = new Board("-O-------");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("XO-------"));
        l.add(new Board("-O-X-----"));
        l.add(new Board("-O--X----"));
        l.add(new Board("-O----X--"));
        l.add(new Board("-O-----X-"));
        assertEquals(l, result);
    }

    @Test
    public void testequals() {
        Board b = new Board("---X--O--");
        Board b2 = new Board("------OX-");
        assertEquals(true, b2.equals(b));
    }

    @Test
    public void testChildren7() throws CloneNotSupportedException {
        Board b = new Board("-------O-");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("X------O-"));
        l.add(new Board("-X-----O-"));
        l.add(new Board("---X---O-"));
        l.add(new Board("----X--O-"));
        l.add(new Board("------XO-"));
        assertEquals(l, result);
    }

    @Test
    public void testChildren8() throws CloneNotSupportedException {
        Board b = new Board("------O--");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("X-----O--"));
        l.add(new Board("-X----O--"));
        l.add(new Board("--X---O--"));
        l.add(new Board("---X--O--"));
        l.add(new Board("----X-O--"));
        assertEquals(l, result);
    }

    @Test
    public void testChildren8_2() throws CloneNotSupportedException {
        Board b = new Board("--O------");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("X-O------"));
        l.add(new Board("-XO------"));
        l.add(new Board("--OX-----"));
        l.add(new Board("--O-X----"));
        l.add(new Board("--O---X--"));
        assertEquals(l, result);
    }

    @Test
    public void testChildren9() throws CloneNotSupportedException {
        Board b = new Board("---OXX---");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("O--OXX---"));
        l.add(new Board("-O-OXX---"));
        l.add(new Board("--OOXX---"));
        assertEquals(l, result);
    }

    @Test
    public void testChildren10() throws CloneNotSupportedException {
        Board b = new Board("---OXX---");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("---OXXO--"));
        l.add(new Board("---OXX-O-"));
        l.add(new Board("---OXX--O"));
        assertEquals(l, result);
    }

    @Test
    public void testUCT() {
        Board b = new Board("-O--X--XO");
        MCTS.State s = new MCTS.State(b, null);
        assertEquals(Integer.MAX_VALUE, s.uct(), 0);
    }

    @Test
    public void testUCT2() {
        Board b = new Board("-O--X--XO");
        MCTS.State s = new MCTS.State(b, new MCTS.State(new Board("----X--XO"), null));
        s.simulations = 5;
        s.wins = 1;
        s.father.simulations = 6;
        assertEquals(0.4401444, s.uct(), 5);
    }

    @Test
    public void testUCT3() {
        Board b = new Board("-O--X--X-");
        MCTS.State s = new MCTS.State(b, new MCTS.State(new Board("----X---O"), null));
        s.simulations = 15;
        s.wins= 10;
        s.father.simulations = 20;
        assertEquals(0.74721, s.uct(), 0.00001);
    }

    @Test
    public void testUCT4() {
        Board b = new Board("-O--X----");
        MCTS.State s = new MCTS.State(b, new MCTS.State(new Board("----X----"), null));
        s.simulations = 27;
        s.wins = 12;
        s.father.simulations = 33;
        assertEquals(0.50931, s.uct(), 0.00001);
    }

    @Test
    public void testEquals() {
        Board b = new Board("XOXOXOXOX");
        Board b2 = new Board("XOXOXXXOX");
        assertEquals(false, b.equals(b2));
    }

    @Test
    public void testEquals2() {
        Board b = new Board("-X--O----");
        Board b2 = new Board("---XO----");
        assertEquals(true, b.equals(b2));
    }

    @Test
    public void testEquals5() {
        Board b = new Board("-X-------");
        Board b2 = new Board("---------");
        assertEquals(false, b.equals(b2));
    }

    @Test
    public void testEquals3() {
        Board b = new Board("XX--O----");
        Board b2 = new Board("XX--O----");
        assertEquals(true, b.equals(b2));
    }

    @Test
    public void testBestandWorstUCT() throws CloneNotSupportedException {
        Board b = new Board("OX--OO-XX");
        MCTS.State s = new MCTS.State(b, null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("OXX-OO-XX"), s));
        l.add(new MCTS.State(new Board("OX-XOO-XX"), s));
        l.add(new MCTS.State(new Board("OX--OOXXX"), s));
        l.get(0).simulations = 10;
        l.get(0).wins = 5;
        l.get(1).simulations = 17;
        l.get(1).wins = 7;
        l.get(2).simulations = 13;
        l.get(2).wins = 2;
        s.simulations = 40;
        s.childs = l;

        assertEquals(0.61054, l.get(0).uct(), 0.00001);
        assertEquals(0.49654, l.get(1).uct(), 0.00001);
        assertEquals(0.25079, l.get(2).uct(), 0.00001);
        assertEquals(l.get(0), s.BestUCT());
    }

    @Test
    public void testBestandWorstUCT2() throws CloneNotSupportedException {
        Board b = new Board("----X----");
        MCTS.State s = new MCTS.State(b, null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("O---X----"), s));
        l.add(new MCTS.State(new Board("-O--X----"), s));
        l.get(0).simulations = 46;
        l.get(0).wins = 31;
        l.get(1).simulations = 54;
        l.get(1).wins = 35;
        s.simulations = 100;
        s.childs = l;

        assertEquals(0.731498, l.get(0).uct(), Math.pow(10,-5));
        assertEquals(0.70129, l.get(1).uct(), Math.pow(10,-5));
        assertEquals(l.get(0), s.BestUCT());
    }

    @Test
    public void testBestandWorstUCT3() throws CloneNotSupportedException {
        Board b = new Board("OX--OO-X-");
        MCTS.State s = new MCTS.State(b, null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("OXX-OO-X-"), s));
        l.add(new MCTS.State(new Board("OX-XOO-X-"), s));
        l.add(new MCTS.State(new Board("OX--OOXX-"), s));
        l.add(new MCTS.State(new Board("OX--OO-XX"), s));
        l.get(0).simulations = 15;
        l.get(0).wins = 5;
        l.get(1).simulations = 20;
        l.get(1).wins = 8;
        l.get(2).simulations = 20;
        l.get(2).wins = 8;
        l.get(3).simulations = 25;
        l.get(3).wins = 12;
        s.simulations = 80;
        s.childs = l;
        assertEquals(0.43170, l.get(0).uct(), 0.00001);
        assertEquals(0.48519, l.get(1).uct(), 0.00001);
        assertEquals(0.48519, l.get(2).uct(), 0.00001);
        assertEquals(0.55619, l.get(3).uct(), 0.00001);
        assertEquals(l.get(3), s.BestUCT());
    }


    @Test
    public void testBestandWorstUCT4() throws CloneNotSupportedException {
        Board b = new Board("OX--X----");
        MCTS.State s = new MCTS.State(b, null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("OXO-X----"), s));
        l.add(new MCTS.State(new Board("OX-OX----"), s));
        l.add(new MCTS.State(new Board("OX--XO---"), s));
        l.add(new MCTS.State(new Board("OX--X-O--"), s));
        l.add(new MCTS.State(new Board("OX--X--O-"), s));
        l.add(new MCTS.State(new Board("OX--X---O"), s));
        l.get(0).simulations = 15;
        l.get(0).wins = 5;

        l.get(1).simulations = 20;
        l.get(1).wins = 8;

        l.get(2).simulations = 20;
        l.get(2).wins = 8;

        l.get(3).simulations = 25;
        l.get(3).wins = 12;

        l.get(4).simulations = 20;
        l.get(4).wins = 7;

        l.get(5).simulations = 20;
        l.get(5).wins = 3;

        s.simulations = 120;
        s.childs = l;
        assertEquals(0.43615, l.get(0).uct(), 0.00001);
        assertEquals(0.48905, l.get(1).uct(), 0.00001);
        assertEquals(0.48905, l.get(2).uct(), 0.00001);
        assertEquals(0.55964, l.get(3).uct(), 0.00001);
        assertEquals(0.43905, l.get(4).uct(), 0.00001);
        assertEquals(0.23905, l.get(5).uct(), 0.00001);
        
        assertEquals(l.get(3), s.BestUCT());
    }

    @Test
    public void testexpand() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("OX--OO-X-"), null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("OXX-OO-X-"), s));
        l.add(new MCTS.State(new Board("OX-XOO-X-"), s));
        l.add(new MCTS.State(new Board("OX--OOXX-"), s));
        l.add(new MCTS.State(new Board("OX--OO-XX"), s));
        assertEquals(h.expand(s), l);
    }

    @Test
    public void testexpand22() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        Board p = new Board("-O-XX----");
        MCTS.State s = new MCTS.State(p, null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("OO-XX----"), s));
        l.add(new MCTS.State(new Board("-OOXX----"), s));
        l.add(new MCTS.State(new Board("-O-XXO---"), s));
        l.add(new MCTS.State(new Board("-O-XX-O--"), s));
        l.add(new MCTS.State(new Board("-O-XX--O-"), s));
        l.add(new MCTS.State(new Board("-O-XX---O"), s));
        // List<MCTS.State> l = new ArrayList<>();
        //System.out.println(h.expand(s));
        assertEquals(h.expand(s), l);
    }

    @Test
    public void testexpand2() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("---------"), null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("X--------"), s));
        l.add(new MCTS.State(new Board("-X-------"), s));
        l.add(new MCTS.State(new Board("----X----"), s));
        assertEquals(h.expand(s), l);
    }

    @Test
    public void testexpand3() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("----O----"), null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("X---O----"), s));
        l.add(new MCTS.State(new Board("-X--O----"), s));
        assertEquals(h.expand(s), l);
    }

    @Test
    public void testexpand4() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("OX--OO-XX"), null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("OXX-OO-XX"), s));
        l.add(new MCTS.State(new Board("OX-XOO-XX"), s));
        l.add(new MCTS.State(new Board("OX--OOXXX"), s));
        assertEquals(h.expand(s), l);
    }

    @Test
    public void testsimulation() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("---------"), null);
        s.childs.add(new MCTS.State(new Board("X--------"), s));
        s.childs.add(new MCTS.State(new Board("-X-------"), s));
        s.childs.add(new MCTS.State(new Board("----X----"), s));
        h.root = s;
        MCTS.State g = h.simulation(s);
        assertEquals(true, s.simulations == 3);
        assertEquals(g, s);
    }

    @Test
    public void testsimulation2() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("OX--OO-X-"), null);
        s.childs.add(new MCTS.State(new Board("OXX-OO-X-"), s));
        s.childs.add(new MCTS.State(new Board("OX-XOO-X-"), s));
        s.childs.add(new MCTS.State(new Board("OX--OOXX-"), s));
        s.childs.add(new MCTS.State(new Board("OX--OO-XX"), s));
        h.root = s;
        MCTS.State g = h.simulation(s);
        assertEquals(true, s.simulations == 4);
        assertEquals(g, s);
    }

    @Test
    public void testexpand5() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("XX-OO----"), null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("XXXOO----"), s));
        l.add(new MCTS.State(new Board("XX-OOX---"), s));
        l.add(new MCTS.State(new Board("XX-OO-X--"), s));
        l.add(new MCTS.State(new Board("XX-OO--X-"), s));
        l.add(new MCTS.State(new Board("XX-OO---X"), s));
        assertEquals(h.expand(s), l);
    }

    @Test
    public void testsimulation3() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        Board b = new Board("XX-OO----");
        MCTS.State s = new MCTS.State(b, null);
        s.childs.add(new MCTS.State(new Board("XXXOO----"), s));
        s.childs.add(new MCTS.State(new Board("XX-OOX---"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO-X--"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO--X-"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO---X"), s));
        h.root = s;
        // System.out.println(h.root.layout.getplayer());
        MCTS.State g = h.simulation(s);
        assertEquals(true, s.simulations == 5);
        assertEquals(1, s.childs.get(0).wins, 0);
        assertEquals(g, s);
    }

    @Test
    public void testsimulation4() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("-OOOXXXXO"), null);
        s.childs.add(new MCTS.State(new Board("XOOOXXXXO"), s));
        h.root = s;
        MCTS.State g = h.simulation(s.childs.get(0));
        assertEquals(true, (s.simulations > 0 && s.childs.get(0).wins == 0.5));
        assertEquals(g, s);
    }

    @Test
    public void testBack() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("---------"), null);
        s.childs.add(new MCTS.State(new Board("X--------"), s));
        s.childs.add(new MCTS.State(new Board("-X-------"), s));
        s.childs.add(new MCTS.State(new Board("----X----"), s));
        h.root = s;
        MCTS.State g = h.backpropagation(s.childs.get(0), 1);
        assertEquals(s, g);
        assertEquals(true, s.simulations > 0);
    }

    @Test
    public void testback2() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("OX--OO-X-"), null);
        s.childs.add(new MCTS.State(new Board("OXX-OO-X-"), s));
        s.childs.add(new MCTS.State(new Board("OX-XOO-X-"), s));
        s.childs.add(new MCTS.State(new Board("OX--OOXX-"), s));
        s.childs.add(new MCTS.State(new Board("OX--OO-XX"), s));
        h.root = s;
        MCTS.State g = h.backpropagation(s.childs.get(2), 0);
        assertEquals(true, s.simulations == 1);
        assertEquals(g, s);
    }

    @Test
    public void testback3() throws CloneNotSupportedException {
        MCTS h = new MCTS();
        MCTS.State s = new MCTS.State(new Board("XX-OO----"), null);
        s.childs.add(new MCTS.State(new Board("XXXOO----"), s));
        s.childs.add(new MCTS.State(new Board("XX-OOX---"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO-X--"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO--X-"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO---X"), s));
        h.root = s;
        // System.out.println(s.childs.get(0).wins);
        MCTS.State g = h.backpropagation(s.childs.get(0), 1);
        g = h.backpropagation(s.childs.get(1), 0);
        g = h.backpropagation(s.childs.get(2), 0.5);
        g = h.backpropagation(s.childs.get(3), 0);
        g = h.backpropagation(s.childs.get(4), 0);
        assertEquals(true, s.simulations == 5);
        assertEquals(true, s.childs.get(0).simulations == 1 && s.childs.get(0).wins == 1);
        assertEquals(true, s.childs.get(1).simulations == 1 && s.childs.get(1).wins == 0);
        assertEquals(true, s.childs.get(2).simulations == 1 && s.childs.get(2).wins == 0.5);
        assertEquals(true, s.childs.get(3).simulations == 1 && s.childs.get(3).wins == 0);
        assertEquals(true, s.childs.get(4).simulations == 1 && s.childs.get(4).wins == 0);
        assertEquals(g, s);
    }

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
}
