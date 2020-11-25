/*import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class Tests {
    @Test
    public void testConstructor() {
        Board b = new Board("000000000");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println(" | | ");
        pw.println("------");
        pw.println(" | | ");
        pw.println("------");
        pw.println(" | | ");
        assertEquals(b.toString(), writer.toString());

    }

    @Test
    public void testConstructor1() {
        Board b = new Board("0X00O00X0");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println(" |X| ");
        pw.println("------");
        pw.println(" |O| ");
        pw.println("------");
        pw.println(" |X| ");
        assertEquals(b.toString(), writer.toString());

    }

    @Test
    public void testConstructor2() {
        Board b = new Board("XX00OO0X0");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("X|X| ");
        pw.println("------");
        pw.println(" |O|O");
        pw.println("------");
        pw.println(" |X| ");
        assertEquals(b.toString(), writer.toString());

    }

    @Test
    public void testConstructor3() {
        Board b = new Board("XXXXOXXXX");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("X|X|X");
        pw.println("------");
        pw.println("X|O|X");
        pw.println("------");
        pw.println("X|X|X");
        assertEquals(b.toString(), writer.toString());

    }

    @Test
    public void testConstructor4() {
        Board b = new Board("OXOOOOOXO");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("O|X|O");
        pw.println("------");
        pw.println("O|O|O");
        pw.println("------");
        pw.println("O|X|O");
        assertEquals(b.toString(), writer.toString());

    }

    @Test
    public void testChildren1() throws CloneNotSupportedException
    {
    	Board b = new Board("000000000");
		List<Ilayout> result = b.children();
		List<Ilayout> l = new ArrayList<Ilayout>();
		l.add(new Board("X00000000"));
		l.add(new Board("0X0000000"));
		l.add(new Board("00X000000"));
		l.add(new Board("000X00000"));
		l.add(new Board("0000X0000"));
		l.add(new Board("00000X000"));
		l.add(new Board("000000X00"));
		l.add(new Board("0000000X0"));
		l.add(new Board("00000000X"));
		assertEquals(result, l);
    }
    
    @Test
    public void testChildren2() throws CloneNotSupportedException
    {
    	Board b = new Board("XXXXXXXXX");
		List<Ilayout> result = b.children();
		List<Ilayout> l = new ArrayList<Ilayout>();
		assertEquals(result, l);
    }
    
    @Test
    public void testChildren3() throws CloneNotSupportedException
    {
    	Board b = new Board("XOX0XX0XO");
		List<Ilayout> result = b.children();
		List<Ilayout> l = new ArrayList<Ilayout>();
		l.add(new Board("XOXXXX0XO"));
		l.add(new Board("XOX0XXXXO"));
		assertEquals(result, l);
    }
    
    @Test
    public void testChildren4() throws CloneNotSupportedException
    {
    	Board b = new Board("0O00X00XO");
		List<Ilayout> result = b.children();
		List<Ilayout> l = new ArrayList<Ilayout>();
		l.add(new Board("XO00X00XO"));
		l.add(new Board("0OX0X00XO"));
		l.add(new Board("0O0XX00XO"));
		l.add(new Board("0O00XX0XO"));
		l.add(new Board("0O00X0XXO"));
		assertEquals(result, l);
    }

    @Test
    public void testWinningwinningVerification()
    {
        Board b = new Board("000000000");
        int result = b.winningVerification();
        assertEquals(0, result);
    }

    @Test
    public void testWinningVerification1()
    {
        Board b = new Board("XXX000000");
        int result = b.winningVerification();
        assertEquals(1, result);
    }

    @Test
    public void testWinningVerification2()
    {
        Board b = new Board("000XXX000");
        int result = b.winningVerification();
        assertEquals(1, result);
    }

    @Test
    public void testWinningVerification3()
    {
        Board b = new Board("000000XXX");
        int result = b.winningVerification();
        assertEquals(1, result);
    }

    @Test
    public void testWinningVerification4()
    {
        Board b = new Board("X00X00X00");
        int result = b.winningVerification();
        assertEquals(1, result);
    }

    @Test
    public void testWinningVerification5()
    {
        Board b = new Board("0X00X00X0");
        int result = b.winningVerification();
        assertEquals(1, result);
    }

    @Test
    public void testWinningVerification6()
    {
        Board b = new Board("00X00X00X");
        int result = b.winningVerification();
        assertEquals(1, result);
    }

    @Test
    public void testWinningVerification7()
    {
        Board b = new Board("X000X000X");
        int result = b.winningVerification();
        assertEquals(1, result);
    }

    @Test
    public void testWinningVerification8()
    {
        Board b = new Board("00X0X0X00");
        int result = b.winningVerification();
        assertEquals(1, result);
    }

    @Test
    public void testWinningVerification9()
    {
        Board b = new Board("XO00OXX0O");
        int result = b.winningVerification();
        assertEquals(0, result);
    }

    @Test
    public void testWinningVerification10()
    {
        Board b = new Board("XX00X0X00");
        int result = b.winningVerification();
        assertEquals(0, result);
    }
}*/