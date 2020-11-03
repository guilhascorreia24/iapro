/*import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;


public class PuzzleUnitTests {
    @Test
    public void testConstructor() {
        Board b = new Board("AB C");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("[A, B]");
        pw.println("[C]");
        assertEquals(b.toString(), writer.toString());

    }

    @Test
    public void testConstructor2() {
        Board b = new Board("ABC");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("[A, B, C]");
        pw.flush();
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test
    public void testConstructor3() {
        Board b = new Board("A B C");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("[A]");
        pw.println("[B]");
        pw.println("[C]");
        pw.flush();
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test(expected= IllegalStateException.class)
    public void testConstructor4() {
        Board b = new Board("");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.flush();
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test(expected= IllegalStateException.class)
    public void testConstructor5() {
        Board b = new Board("A");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.flush();
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test
    public void test() throws CloneNotSupportedException {
        //long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        Board b=new Board("CAB");
        Board b2=new Board("ABC");
        BestFirst s = new BestFirst();
        List<Ilayout> prog=new ArrayList<Ilayout>();
        prog.add(new Board("CAB"));prog.add(new Board("CA B"));prog.add(new Board("C B A"));prog.add(new Board("C AB"));prog.add(new Board("ABC"));
        Iterator<Ilayout> it2=prog.iterator();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            assertEquals(i.toString(),it2.next().toString());
            if (!it.hasNext())
                assertEquals(4,(int)i.getG());
        }
        //long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        //long actualMemUsed=afterUsedMem-beforeUsedMem;
        //System.out.println(actualMemUsed+" "+beforeUsedMem);
    }

    @Test
    public void test2() throws CloneNotSupportedException {
        Board b=new Board("AC B");
        Board b2=new Board("A B C");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        List<Ilayout> prog=new ArrayList<>();
        prog.add(new Board("AC B"));prog.add(new Board("A B C"));
        Iterator<Ilayout> it2=prog.iterator();
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            assertEquals(i.toString(),it2.next().toString());
            if (!it.hasNext())
                assertEquals(1,(int)i.getG());
        }
    }

    @Test
    public void test3() throws CloneNotSupportedException {
        Board b=new Board("A BC");
        Board b2=new Board("C AB");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        List<Ilayout> prog=new ArrayList<>();
        prog.add(new Board("A BC"));prog.add(new Board("A B C"));prog.add(new Board("AB C"));
        Iterator<Ilayout> it2=prog.iterator();
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            assertEquals(i.toString(),it2.next().toString());
            if (!it.hasNext())
                assertEquals(2,(int)i.getG());
        }
    }

    @Test
    public void test4() throws CloneNotSupportedException {
        Board b=new Board("ACB");
        Board b2=new Board("BCA");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        List<Ilayout> prog=new ArrayList<>();
        prog.add(new Board("ACB"));prog.add(new Board("AC B"));prog.add(new Board("A BC"));prog.add(new Board("BCA"));
        Iterator<Ilayout> it2=prog.iterator();
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            //System.out.println(i);
            assertEquals(i.toString(),it2.next().toString());
            if (!it.hasNext())
                assertEquals(3,(int)i.getG());
        }
    }

    @Test
    public void testchildren() throws CloneNotSupportedException {
        Board b=new Board("AB C");
        List<Ilayout> children = b.children();
        List<Ilayout> child=new ArrayList<Ilayout>();
        child.add(new Board("A C B"));child.add(new Board("AB C"));child.add(new Board("A CB"));child.add(new Board("AB C"));child.add(new Board("ABC"));
        assertEquals(child, children);
    }

    @Test
    public void testchildren2() throws CloneNotSupportedException {
        Board b=new Board("ABC");
        List<Ilayout> children = b.children();
        List<Ilayout> child=new ArrayList<Ilayout>();
        child.add(new Board("AB C"));child.add(new Board("ABC"));
        assertEquals(child, children);
    }

    @Test
    public void testchildren3() throws CloneNotSupportedException {
        Board b=new Board("A B C");
        List<Ilayout> children = b.children();
        List<Ilayout> child=new ArrayList<Ilayout>();
        child.add(new Board("B C A"));child.add(new Board("BA C"));child.add(new Board("B CA"));child.add(new Board("A C B"));child.add(new Board("AB C"));child.add(new Board("A CB"));child.add(new Board("A B C"));child.add(new Board("AC B"));child.add(new Board("A BC"));
        assertEquals(child, children);
    }

    @Test
    public void testchildren4() throws CloneNotSupportedException {
        Board b=new Board("A BC");
        List<Ilayout> children = b.children();
        List<Ilayout> child=new ArrayList<Ilayout>();
        child.add(new Board("BC A"));child.add(new Board("BCA"));child.add(new Board("A B C"));child.add(new Board("AC B"));
        assertEquals(child, children);
    }


    @Test
    public void testequals(){
        Board b=new Board("ABC");
        Board b2=new Board("CBA");
        assertEquals(b.equals(b2), false);
    }

    @Test
    public void testequals2(){
        Board b=new Board("AB C");
        Board b2=new Board("C BA");
        assertEquals(b.equals(b2),false);
    }

    @Test
    public void testequals3(){
        Board b=new Board("AB C");
        Board b2=new Board("C AB");
        assertEquals(b.equals(b2),true);
    }

    @Test
    public void testequals4(){
        Board b=new Board("A B C");
        Board b2=new Board("C B A");
        assertEquals(b.equals(b2),true);
    }

    @Test
    public void testeclone() throws CloneNotSupportedException {
        Board b=new Board("ABC");
        assertEquals(b.clone(),b);
    }

    @Test
    public void testclone2() throws CloneNotSupportedException {
        Board b=new Board("A B C");
        assertEquals(b.clone(),b);
    }
}*/