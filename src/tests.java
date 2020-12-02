/*import static org.junit.Assert.assertEquals;

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
    public void testConstructorState0() {
        Board b = new Board("-X--O--X-");
        BestFirst.State s=new BestFirst.State(b,null);
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("-X-");
        pw.println("-O-");
        pw.println("-X-");
        assertEquals(false, s.isfinalnode());
        assertEquals(writer.toString(), b.toString());
    }

    @Test
    public void testConstructorState() {
        Board b = new Board("OXOOOOOXO");
        BestFirst.State s=new BestFirst.State(b,null);
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
		l.add(new Board("--X------"));
		l.add(new Board("---X-----"));
		l.add(new Board("----X----"));
		l.add(new Board("-----X---"));
		l.add(new Board("------X--"));
		l.add(new Board("-------X-"));
		l.add(new Board("--------X"));
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
    public void testChildren4() throws CloneNotSupportedException
    {
    	Board b = new Board("-O--X--XO");
		List<Ilayout> result = b.children();
		List<Ilayout> l = new ArrayList<Ilayout>();
		l.add(new Board("XO--X--XO"));
		l.add(new Board("-OX-X--XO"));
		l.add(new Board("-O-XX--XO"));
		l.add(new Board("-O--XX-XO"));
		l.add(new Board("-O--X-XXO"));
		assertEquals(l, result);
    }

    @Test
    public void testUCT(){
        Board b = new Board("-O--X--XO");
        BestFirst.State s = new BestFirst.State(b,null);
        assertEquals(Integer.MAX_VALUE,s.uct(),0);
    }
    @Test
    public void testUCT2(){
        Board b = new Board("-O--X--XO");
        BestFirst.State s = new BestFirst.State(b, new BestFirst.State(new Board("----X--XO"), null));
        s.setSim(5);
        s.setWin(1);
        s.getFather().setSim(6);
        assertEquals(1.04658,s.uct(),5);
    }

    @Test
    public void testUCT3(){
        Board b = new Board("-O--X--X-");
        BestFirst.State s = new BestFirst.State(b, new BestFirst.State(new Board("----X---O"), null));
        s.setSim(15);
        s.setWin(10);
        s.getFather().setSim(20);
        assertEquals(1.29867,s.uct(),5);
    }

    @Test
    public void testUCT4(){
        Board b = new Board("-O--X----");
        BestFirst.State s = new BestFirst.State(b, new BestFirst.State(new Board("----X----"), null));
        s.setSim(27);
        s.setWin(12);
        s.getFather().setSim(33);
        assertEquals(0.95337,s.uct(),5);
    }

    @Test
    public void testRotation(){
        Board b = new Board("X---O----");
        Board b2 = ((Board) b.clone()).rotate();
        Board b3 = ((Board) b2).rotate();
        Board b4 = ((Board) b3).rotate();
        Board b5 = ((Board) b4).rotate();
        assertEquals(new Board("--X-O----"), b2);
        assertEquals(new Board("----O---X"), b3);
        assertEquals(new Board("----O-X--"), b3);
        assertEquals(b, b5);
        //System.out.println(b+"\n"+b2+"\n"+b.equals(b2));
    }

    @Test
    public void testRotation2(){
        Board b = new Board("XOXOOOXOX");
        Board b2 = ((Board) b.clone()).rotate();
        Board b3 = ((Board) b2).rotate();
        Board b4 = ((Board) b3).rotate();
        Board b5 = ((Board) b4).rotate();
        assertEquals(b, b2);
        assertEquals(b, b3);
        assertEquals(b, b4);
        assertEquals(b, b5);
    }

    @Test
    public void testRotation3(){
        Board b = new Board("OXXOXOOXX");
        Board b2 = ((Board) b.clone()).rotate();
        Board b3 = ((Board) b2).rotate();
        Board b4 = ((Board) b3).rotate();
        Board b5 = ((Board) b4).rotate();
        assertEquals(new Board("OOOXXXXOX"), b2);
        assertEquals(new Board("XXOOXOXXO"), b3);
        assertEquals(new Board("XOXXXXOOO"), b4);
        assertEquals(b, b5);
    }

    @Test
    public void testchilden5() throws CloneNotSupportedException {
        Board b=new Board("----O----");
        System.out.println(b.children());
    }
}*/