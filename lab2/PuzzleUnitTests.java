import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;


public class PuzzleUnitTests {
    @Test
    public void testConstructor() {
        Board b = new Board("A C");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("[A]");
        pw.println("[C]");
        assertEquals(b.toString(), writer.toString());

    }

    @Test
    public void testConstructor6() {
        Board b = new Board("A B C D E F G");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("[A]");
        pw.println("[B]");
        pw.println("[C]");
        pw.println("[D]");
        pw.println("[E]");
        pw.println("[F]");
        pw.println("[G]");
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

    @Test
    public void testConstructor4() {
        Board b = new Board("");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.flush();
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test
    public void testConstructor5() {
        Board b = new Board("A");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("[A]");
        pw.flush();
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test
    public void test() throws CloneNotSupportedException {
        //long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long startTime = System.nanoTime();
        Board b=new Board("CAB");
        Board b2=new Board("ABC");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(4,(int)i.getG());
        }
        //long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        //long actualMemUsed=afterUsedMem-beforeUsedMem;
        //System.out.println(actualMemUsed+" "+beforeUsedMem);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t1:"+(duration/1000000)*0.001  +"s");
    }

    @Test
    public void test2() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("AC B");
        Board b2=new Board("A B C");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(1,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t2:"+(duration/1000000)*0.001  +"s");
    }

    @Test
    public void test3() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("A BC");
        Board b2=new Board("C AB");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(2,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t3:"+(duration/1000000)*0.001  +"s");
    }

    @Test
    public void test4() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("ACB");
        Board b2=new Board("BCA");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(3,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t4:"+(duration/1000000)*0.001  +"s");
    }

    @Test
    public void test5() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("GF EDC B A");
        Board b2=new Board("ABCDEFG");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(6,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t5:"+(duration/1000000)*0.001  +"s");
    }

    @Test
    public void test6() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("ABCDEFG");
        Board b2=new Board("DEACGFB");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(12,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t6:"+(duration/1000000)*0.001  +"s");
    }

    @Test
    public void test7() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("ABCDEFGHIJ");
        Board b2=new Board("DEAJCHGIFB");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                //assertEquals(18,(int)i.getG());
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t7:"+(duration/1000000)*0.001  +"s");
    }

    @Test
    public void test8() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("ABCDEFG");
        Board b2=new Board("D E A C G F B");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                assertEquals(6,(int)i.getG());
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t8:"+(duration/1000000)*0.001  +"s");
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

    @Test
    public void testgetH() throws CloneNotSupportedException {
        Ilayout b=new Board("AB C");
        Ilayout b2Board=new Board("A B C");
        assertEquals(1, b.getH(b2Board),0);
    }

    @Test
    public void testgetH2() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCD");
        Ilayout b2Board=new Board("A B C D");
        assertEquals(3, b.getH(b2Board),0);
    }

}