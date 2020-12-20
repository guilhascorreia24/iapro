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
        pw.println("O");
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
        pw.println("X");
        assertEquals(writer.toString(), b.toString());
    }

    @Test
    public void testConstructor2() {
        Board b = new Board("XX--OO-X-");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("XX-");
        pw.println("-OO");
        pw.println("-X-");
        pw.println("X");
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
        pw.println("X");
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
        pw.println("O");
        assertEquals(writer.toString(), b.toString());
    }

    @Test
    public void testConstructorState() {
        Board b = new Board("-X--O--X-");
        MCTS.State s = new MCTS.State(b, null);
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("-X-");
        pw.println("-O-");
        pw.println("-X-");
        pw.println("X");
        assertEquals(false, s.final_node);
        assertEquals(writer.toString(), b.toString());
    }

    @Test
    public void testConstructorState2() {
        Board b = new Board("OXOOOOOXO");
        MCTS.State s = new MCTS.State(b, null);
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("OXO");
        pw.println("OOO");
        pw.println("OXO");
        pw.println("O");
        assertEquals(true, s.final_node);
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
        //System.out.println(result);
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
    public void testChildren11() throws CloneNotSupportedException {
        Board b = new Board("OOX-X--X-");
        b.character='X';b.counter='O';
        //List<Ilayout> result = b.children();
        //System.out.println(result);
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
        s.s = 5;
        s.w = 1;
        s.father.s = 6;
        assertEquals(0.4401444, s.uct(), 5);
    }

    @Test
    public void testUCT3() {
        Board b = new Board("-O--X--X-");
        MCTS.State s = new MCTS.State(b, new MCTS.State(new Board("----X---O"), null));
        s.s = 15;
        s.w = 10;
        s.father.s = 20;
        assertEquals(1.29867, s.uct(), 0);
    }

    @Test
    public void testUCT4() {
        Board b = new Board("-O--X----");
        MCTS.State s = new MCTS.State(b, new MCTS.State(new Board("----X----"), null));
        s.s = 27;
        s.w = 12;
        s.father.s = 33;
        assertEquals(0.95337, s.uct(), 0);
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
        l.get(0).s = 10;
        l.get(0).w = 5;
        l.get(1).s = 17;
        l.get(1).w = 7;
        l.get(2).s = 13;
        l.get(2).w = 2;
        s.s = 40;
        s.childs = l;

        assertEquals(1.35894, l.get(0).uct(), 0);
        assertEquals(1.07054, l.get(1).uct(), 0);
        assertEquals(0.90719, l.get(2).uct(), 0);
        assertEquals(l.get(0), s.BestUCT());
        assertEquals(l.get(2), s.WorstUCT());
    }

    @Test
    public void testBestandWorstUCT2() throws CloneNotSupportedException {
        Board b = new Board("----X----");
        MCTS.State s = new MCTS.State(b, null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("O---X----"), s));
        l.add(new MCTS.State(new Board("-O--X----"), s));
        l.get(0).s = 46;
        l.get(0).w = 31;
        l.get(1).s = 54;
        l.get(1).w = 35;
        s.s = 100;
        s.childs = l;

        assertEquals(1.12138, l.get(0).uct(), 0);
        assertEquals(1.06114, l.get(1).uct(), 0);
        assertEquals(l.get(0), s.BestUCT());
        assertEquals(l.get(1), s.WorstUCT());
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
        l.get(0).s = 15;
        l.get(0).w = 5;
        l.get(1).s = 20;
        l.get(1).w = 8;
        l.get(2).s = 20;
        l.get(2).w = 8;
        l.get(3).s = 25;
        l.get(3).w = 12; 
        s.s = 80;
        s.childs = l;
        assertEquals(1.09771, l.get(0).uct(), 0);
        assertEquals(1.06197, 0, 0);
        assertEquals(1.06197, l.get(2).uct(), 0);
        assertEquals(1.07208, l.get(3).uct(), 0);
        assertEquals(l.get(3), s.BestUCT());
        assertEquals(l.get(0), s.WorstUCT());
    }

    @Test
    public void testexpand() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("OX--OO-X-"),null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("OXX-OO-X-"), s));
        l.add(new MCTS.State(new Board("OX-XOO-X-"), s));
        l.add(new MCTS.State(new Board("OX--OOXX-"), s));
        l.add(new MCTS.State(new Board("OX--OO-XX"), s));
        assertEquals(h.expand(s), l);
    }

    @Test
    public void testexpand2() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("---------"),null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("X--------"), s));
        l.add(new MCTS.State(new Board("-X-------"), s));
        l.add(new MCTS.State(new Board("----X----"), s));
        assertEquals(h.expand(s), l);
    }
    @Test
    public void testexpand3() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("----O----"),null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("X---O----"), s));
        l.add(new MCTS.State(new Board("-X--O----"), s));
        assertEquals(h.expand(s), l);
    }
    @Test
    public void testexpand4() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("OX--OO-XX"),null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("OXX-OO-XX"), s));
        l.add(new MCTS.State(new Board("OX-XOO-XX"), s));
        l.add(new MCTS.State(new Board("OX--OOXXX"), s));
        assertEquals(h.expand(s), l);
    }

    @Test
    public void testsimulation() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("---------"), null);
        s.childs.add(new MCTS.State(new Board("X--------"), s));
        s.childs.add(new MCTS.State(new Board("-X-------"), s));
        s.childs.add(new MCTS.State(new Board("----X----"), s));
        h.root=s;
        MCTS.State g=h.simulation(s);
        assertEquals(true, s.s==3);
        assertEquals(g, s);
    }

    @Test
    public void testsimulation2() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("OX--OO-X-"), null);
        s.childs.add(new MCTS.State(new Board("OXX-OO-X-"), s));
        s.childs.add(new MCTS.State(new Board("OX-XOO-X-"), s));
        s.childs.add(new MCTS.State(new Board("OX--OOXX-"), s));
        s.childs.add(new MCTS.State(new Board("OX--OO-XX"), s));
        h.root=s;
        MCTS.State g=h.simulation(s);
        assertEquals(true, s.s==4);
        assertEquals(g, s);
    }
    @Test
    public void testexpand5() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("XX-OO----"), null);
        List<MCTS.State> l=new ArrayList<>();
        l.add(new MCTS.State(new Board("XXXOO----"), s));
        l.add(new MCTS.State(new Board("XX-OOX---"), s));
        l.add(new MCTS.State(new Board("XX-OO-X--"), s));
        l.add(new MCTS.State(new Board("XX-OO--X-"), s));
        l.add(new MCTS.State(new Board("XX-OO---X"), s));
        assertEquals(h.expand(s), l);
    }
    @Test
    public void testsimulation3() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        Board b=new Board("XX-OO----");
        MCTS.State s=new MCTS.State(b, null);
        s.childs.add(new MCTS.State(new Board("XXXOO----"), s));
        s.childs.add(new MCTS.State(new Board("XX-OOX---"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO-X--"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO--X-"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO---X"), s));
        h.root=s;
        //System.out.println(h.root.layout.getplayer());
        MCTS.State g=h.simulation(s);
        assertEquals(true, s.s==5);
        assertEquals(1,s.childs.get(0).w,0);
        assertEquals(g, s);
    }
    @Test
    public void testsimulation4() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("-OOOXXXXO"), null);
        s.childs.add(new MCTS.State(new Board("XOOOXXXXO"), s));
        h.root=s;
        MCTS.State g=h.simulation(s.childs.get(0));
        assertEquals(true, (s.s>0 && s.childs.get(0).w==0.5));
        assertEquals(g, s);
    }

    @Test
    public void testBack() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("---------"), null);
        s.childs.add(new MCTS.State(new Board("X--------"), s));
        s.childs.add(new MCTS.State(new Board("-X-------"), s));
        s.childs.add(new MCTS.State(new Board("----X----"), s));
        h.root=s;
        MCTS.State g=h.backpropagation(s.childs.get(0),1);
        assertEquals(s, g);
        assertEquals(true, s.s>0);
    }

    @Test
    public void testback2() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("OX--OO-X-"), null);
        s.childs.add(new MCTS.State(new Board("OXX-OO-X-"), s));
        s.childs.add(new MCTS.State(new Board("OX-XOO-X-"), s));
        s.childs.add(new MCTS.State(new Board("OX--OOXX-"), s));
        s.childs.add(new MCTS.State(new Board("OX--OO-XX"), s));
        h.root=s;
        MCTS.State g=h.backpropagation(s.childs.get(2), 0);
        assertEquals(true, s.s==1);
        assertEquals(g, s);
    }

    @Test
    public void testback3() throws CloneNotSupportedException {
        MCTS h=new MCTS();
        MCTS.State s=new MCTS.State(new Board("XX-OO----"), null);
        s.childs.add(new MCTS.State(new Board("XXXOO----"), s));
        s.childs.add(new MCTS.State(new Board("XX-OOX---"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO-X--"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO--X-"), s));
        s.childs.add(new MCTS.State(new Board("XX-OO---X"), s));
        h.root=s;
        //System.out.println(s.childs.get(0).w);
        MCTS.State g=h.backpropagation(s.childs.get(0), 1);
        g=h.backpropagation(s.childs.get(1), 0);
         g=h.backpropagation(s.childs.get(2), 0.5);
         g=h.backpropagation(s.childs.get(3), 0);
         g=h.backpropagation(s.childs.get(4), 0);
        assertEquals(true, s.s==5);
        assertEquals(true, s.childs.get(0).s==1 && s.childs.get(0).w==1);
        assertEquals(true, s.childs.get(1).s==1 && s.childs.get(1).w==0);
        assertEquals(true, s.childs.get(2).s==1 && s.childs.get(2).w==0.5);
        assertEquals(true, s.childs.get(3).s==1 && s.childs.get(3).w==0);
        assertEquals(true, s.childs.get(4).s==1 && s.childs.get(4).w==0);
        assertEquals(g, s);
    }


    @Test
    public void testPrecision() throws CloneNotSupportedException {
        int i = 0, res = 0;
            while (i < 1000) {
                MCTS s = new MCTS();
                Board b = new Board("---------");
                while (!s.end_game) {
                    b = (Board) s.BestNextMove(b).layout;
                }
                if (b.stateBoard() != 0.5) {
                    res++;
                }
                i++;
            }
            System.out.println(res + "/" + i);
    }
}   