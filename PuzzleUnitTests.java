import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class PuzzleUnitTests {
    @Test
    public void testConstructor() {
        Board b = new Board("023145678");
        /*StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println(" 23");
        pw.println("145");
        pw.println("678");*/
        String s="";
        s+=" 23\n";s+="145\n";s+="678\n";
        assertEquals(b.toString(), s.toString());

    }

    @Test
    public void testConstructor2() {
        Board b = new Board("123485670");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("123");
        pw.println("485");
        pw.println("67 ");
        pw.flush();
        System.out.println(b.toString());
        System.out.println(writer.toString());
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test
    public void testALL() throws CloneNotSupportedException {
        Board b=new Board("123456780");
        Board b2=new Board("436718520");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b, b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(12, (int)i.getG());
        }
    }

    @Test
    public void testALL2() throws CloneNotSupportedException {
        Board b=new Board("023145678");
        Board b2=new Board("123405678");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b, b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(2, (int)i.getG());
        }
    }

    @Test
    public void testALL3() throws CloneNotSupportedException {
        Board b=new Board("123456780");
        Board b2=new Board("123456780");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b, b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(0, (int)i.getG());
        }
    }

    @Test
    public void testALL4() throws CloneNotSupportedException {
        Board b=new Board("023147685");
        Board b2=new Board("123405678");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b, b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(6, (int)i.getG());
        }
    }

    @Test
    public void testALL5() throws CloneNotSupportedException {
        Board b=new Board("216408753");
        Board b2=new Board("281430765");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b, b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(9, (int)i.getG());
        }
    }

    @Test
    public void testALL6() throws CloneNotSupportedException {
        Board b=new Board("283164705");
        Board b2=new Board("283156740");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b, b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(5, (int)i.getG());
        }
    }

    @Test
    public void testequals(){
        Board b=new Board("283164705");
        Board b2=new Board("283156740");
        assertEquals(b.equals(b2),false);
    }

    @Test
    public void testequals2(){
        Board b=new Board("283164705");
        Board b2=new Board("283164705");
        assertEquals(b.equals(b2),true);
    }

    @Test
    public void testeclone() throws CloneNotSupportedException {
        Board b=new Board("283164705");
        assertEquals(b.clone(),b);
    }

    @Test
    public void testclone2() throws CloneNotSupportedException {
        Board b=new Board("283164705");
        assertEquals(b.clone(),b);
    }

    @Test
    public void testechildren() throws CloneNotSupportedException {
        Board b=new Board("023145876");
        List<Ilayout> childs=new ArrayList<>();
        childs.add(new Board("203145876"));childs.add(new Board("123045876"));
        assertEquals(b.children(),childs);
    }

    @Test
    public void testchildren2() throws CloneNotSupportedException {
        Board b=new Board("123456780");
        List<Ilayout> childs=new ArrayList<>();
        childs.add(new Board("123456708"));childs.add(new Board("123450786"));
        assertEquals(b.children(),childs);
    }


}