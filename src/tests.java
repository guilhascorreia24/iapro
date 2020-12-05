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
        pw.println("-X-");
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
    public void testConstructorState() {
        Board b = new Board("-X--O--X-");
        MCTS.State s=new MCTS.State(b,null);
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("-X-");
        pw.println("-O-");
        pw.println("-X-");
        assertEquals(false, s.isfinalnode());
        assertEquals(writer.toString(), b.toString());
    }

    @Test
    public void testConstructorState2() {
        Board b = new Board("OXOOOOOXO");
        MCTS.State s=new MCTS.State(b,null);
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("OXO");
        pw.println("OOO");
        pw.println("OXO");
        assertEquals(true, s.isfinalnode());
        assertEquals(writer.toString(), b.toString());
    }
    
    @Test
    public void testChildren1() throws CloneNotSupportedException
    {
    	Board b = new Board("---------");
		List<Ilayout> result = b.children();
		List<Ilayout> l = new ArrayList<Ilayout>();
		l.add(new Board("X--------"));
		l.add(new Board("-X-------"));
        l.add(new Board("----X----"));
		assertEquals(l, result);
    }
    
    @Test
    public void testChildren2() throws CloneNotSupportedException
    {
    	Board b = new Board("XXXXXXXXX");
		List<Ilayout> result = b.children();
		List<Ilayout> l = new ArrayList<Ilayout>();
		assertEquals(l, result);
    }
    
    @Test
    public void testChildren3() throws CloneNotSupportedException
    {
    	Board b = new Board("XOX-XX-XO");
		List<Ilayout> result = b.children();
		List<Ilayout> l = new ArrayList<Ilayout>();
		l.add(new Board("XOXXXX-XO"));
		l.add(new Board("XOX-XXXXO"));
		assertEquals(l, result);
    }

    @Test
    public void testChildren5() throws CloneNotSupportedException
    {
    	Board b = new Board("----O----");
		List<Ilayout> result = b.children();
		List<Ilayout> l = new ArrayList<Ilayout>();
		l.add(new Board("X---O----"));
        l.add(new Board("-X--O----"));
		assertEquals(l, result);
    }
    
    @Test
    public void testChildren4() throws CloneNotSupportedException
    {
    	Board b = new Board("-O--X--XO");
        List<Ilayout> result = b.children();
        System.out.println(result);
		List<Ilayout> l = new ArrayList<Ilayout>();
		l.add(new Board("XO--X--XO"));
		l.add(new Board("-OX-X--XO"));
		l.add(new Board("-O-XX--XO"));
		l.add(new Board("-O--XX-XO"));
		l.add(new Board("-O--X-XXO"));
		assertEquals(l, result);
    }

    @Test
    public void testUCT()
    {
        Board b = new Board("-O--X--XO");
        MCTS.State s = new MCTS.State(b,null);
        assertEquals(Integer.MAX_VALUE,s.uct(),0);
    }
    
    @Test
    public void testUCT2()
    {
        Board b = new Board("-O--X--XO");
        MCTS.State s = new MCTS.State(b, new MCTS.State(new Board("----X--XO"), null));
        s.n=5;
        s.w=1;
        s.father.n=6;
        assertEquals(1.04658,s.uct(),5);
    }

    @Test
    public void testUCT3()
    {
        Board b = new Board("-O--X--X-");
        MCTS.State s = new MCTS.State(b, new MCTS.State(new Board("----X---O"), null));
        s.n=15;
        s.w=10;
        s.father.n=20;
        assertEquals(1.29867,s.uct(),5);
    }

    @Test
    public void testUCT4()
    {
        Board b = new Board("-O--X----");
        MCTS.State s = new MCTS.State(b, new MCTS.State(new Board("----X----"), null));
        s.n=27;
        s.w=12;
        s.father.n=33;
        assertEquals(0.95337,s.uct(),5);
    }

    @Test
    public void testEquals()
    {
        Board b = new Board("XOXOXOXOX");
        Board b2 = new Board("XOXOXOXOX");
        assertEquals(true, b.equals(b2));
    }

    @Test
    public void testEquals2()
    {
        Board b = new Board("-X--O----");
        Board b2 = new Board("---XO----");
        assertEquals(true, b.equals(b2));
    }

    @Test
    public void testEquals3()
    {
        Board b = new Board("XX--O----");
        Board b2 = new Board("XX--O----");
        assertEquals(true, b.equals(b2));
    }

    @Test
    public void testRotate()
    {
        Board b = new Board("XOXOXOXOX");
        Board b2 = b.rotate();
        Board b3 = b2.rotate();
        Board b4 = b3.rotate();
        Board b5 = b4.rotate();
        assertEquals(b, b2);
        assertEquals(b, b3);
        assertEquals(b, b4);
        assertEquals(b, b5);
    }

    @Test
    public void testRotate2()
    {
        Board b = new Board("----X----");
        Board result = b.rotate();
        assertEquals(new Board("----X----"), result);
    }

    @Test
    public void testRotate3()
    {
        Board b = new Board("OXXOXOOXX");
        Board b2 = b.rotate();
        Board b3 = b2.rotate();
        Board b4 = b3.rotate();
        Board b5 = b4.rotate();
        assertEquals(new Board("XOXXXXOOO"), b2);
        assertEquals(new Board("XXOOXOXXO"), b3);
        assertEquals(new Board("OOOXXXXOX"), b4);
        assertEquals(b, b5);
    }

    @Test
    public void testRotate4()
    {
        Board b = new Board("OX--OO-XX");
        Board b2 = b.rotate();
        Board b3 = b2.rotate();
        Board b4 = b3.rotate();
        Board b5 = b4.rotate();
        assertEquals(new Board("-OXXOXO--"), b2);
        assertEquals(new Board("XX-OO--XO"), b3);
        assertEquals(new Board("--OXOXXO-"), b4);
        assertEquals(b, b5);
    }
    
    @Test
    public void testBestandWorstUCT() throws CloneNotSupportedException
    {
        Board b = new Board("OX--OO-XX");
        MCTS.State s=new MCTS.State(b, null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("OXX-OO-XX"),s));
        l.add(new MCTS.State(new Board("OX-XOO-XX"),s));
        l.add(new MCTS.State(new Board("OX--OOXXX"),s));
        l.get(0).n=10;l.get(0).w=5;
        l.get(1).n=17;l.get(1).w=7;
        l.get(2).n=13;l.get(2).w=2;
        s.n=40;
        s.childs=l;
        
        assertEquals(1.35894,l.get(0).uct(),5);
        assertEquals(1.07054,l.get(1).uct(),5);
        assertEquals(0.90719,l.get(2).uct(),5);
        assertEquals(l.get(0),s.BestUCT());
        assertEquals(l.get(2),s.WorstUCT());
    }


    @Test
    public void testBestandWorstUCT2() throws CloneNotSupportedException
    {
        Board b = new Board("----X----");
        MCTS.State s=new MCTS.State(b, null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("O---X----"),s));
        l.add(new MCTS.State(new Board("-O--X----"),s));
        l.get(0).n=46;l.get(0).w=31;
        l.get(1).n=54;l.get(1).w=35;
        s.n=100;
        s.childs=l;
        
        assertEquals(1.12138,l.get(0).uct(),5);
        assertEquals(1.06114,l.get(1).uct(),5);
        assertEquals(l.get(0),s.BestUCT());
        assertEquals(l.get(1),s.WorstUCT());
    }

    @Test
    public void testBestandWorstUCT3() throws CloneNotSupportedException
    {
        Board b = new Board("OX--OO-X-");
        MCTS.State s=new MCTS.State(b, null);
        List<MCTS.State> l = new ArrayList<>();
        l.add(new MCTS.State(new Board("OXX-OO-X-"),s));
        l.add(new MCTS.State(new Board("OX-XOO-X-"),s));
        l.add(new MCTS.State(new Board("OX--OOXX-"),s));
        l.add(new MCTS.State(new Board("OX--OO-XX"),s));
        l.get(0).n=15;l.get(0).w=5;
        l.get(1).n=20;l.get(1).w=8;
        l.get(2).n=20;l.get(2).w=8;
        l.get(3).n=25;l.get(3).w=12;
        s.n=80;
        s.childs=l;
        
        assertEquals(1.09771,l.get(0).uct(),5);
        assertEquals(1.06197,l.get(1).uct(),5);
        assertEquals(1.06197,l.get(2).uct(),5);
        assertEquals(1.07208,l.get(3).uct(),5);
        assertEquals(l.get(0),s.BestUCT());
        assertEquals(l.get(1),s.WorstUCT());
    }




}