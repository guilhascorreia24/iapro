/*import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
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


    

}*/