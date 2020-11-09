/*import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;


public class PuzzleUnitTests {
    NumberFormat formatter = new DecimalFormat("#.###");
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
        long startTime = System.nanoTime();
        Board b=new Board("CAB");
        Board b2=new Board("ABC");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.Ida(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(4,(int)i.getG());
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t1:"+formatter.format(duration*0.000001)  +"ms");
    }

    @Test
    public void test2() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("AC B");
        Board b2=new Board("A B C");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.Ida(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(1,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t2:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test3() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("A BC");
        Board b2=new Board("C AB");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.Ida(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(2,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t3:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test4() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("ACB");
        Board b2=new Board("BCA");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.Ida(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(3,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t4:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test5() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("GF EDC B A");
        Board b2=new Board("ABCDEFG");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.Ida(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(6,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t5:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test6() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("ABCDEFG");
        Board b2=new Board("BCDEFGA");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.Ida(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(12,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t6:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test (expected = IllegalStateException.class)
    public void test7() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("ABCDEFGHIJ");
        Board b2=new Board("DEAJCHGIFB");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.Ida(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                assertEquals(18,(int)i.getG());
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t7:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test8() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("ABCDEFG");
        Board b2=new Board("D E A C G F B");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.Ida(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                assertEquals(6,(int)i.getG());
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t8:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test0_1() throws CloneNotSupportedException {
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
        System.out.println("t11:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test2_1() throws CloneNotSupportedException {
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
        System.out.println("t21:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test3_1() throws CloneNotSupportedException {
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
        System.out.println("t31:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test4_1() throws CloneNotSupportedException {
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
        System.out.println("t41:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test5_1() throws CloneNotSupportedException {
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
        System.out.println("t51:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test6_1() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("ABCDEFG");
        Board b2=new Board("BCDEFGA");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext())
                assertEquals(12,(int)i.getG());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t61:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test(expected = IllegalStateException.class)
    public void test7_1() throws CloneNotSupportedException {
        long startTime = System.nanoTime();
        Board b=new Board("ABCDEFGHIJ");
        Board b2=new Board("DEAJCHGIFB");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                assertEquals(18,(int)i.getG());
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("t71:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void test8_1() throws CloneNotSupportedException {
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
        System.out.println("t81:"+formatter.format(duration*0.000001)   +"ms");
    }

    @Test
    public void testChildren() throws CloneNotSupportedException
    {
        Board b = new Board("A CB");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("CBA"));
        //l.add(new Board("CB A"));
        l.add(new Board("AB C"));
        l.add(new Board("A C B"));
        assertEquals(result, l);
    }

    @Test
    public void testChildren2() throws CloneNotSupportedException
    {
        Board b = new Board("ABC");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("AB C"));
        assertEquals(result, l);
    }

    @Test
    public void testChildren3() throws CloneNotSupportedException
    {
        Board b = new Board("A B C");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("BA C"));
        l.add(new Board("B CA"));
        l.add(new Board("AB C"));
        l.add(new Board("A CB"));
        l.add(new Board("AC B"));
        l.add(new Board("A BC"));
        assertEquals(result, l);
    }

    @Test
    public void testChildren4() throws CloneNotSupportedException
    {
        Board b = new Board("AB C");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("A CB"));
        l.add(new Board("A C B"));
        l.add(new Board("ABC"));
        //l.add(new Board("AB C"));
        assertEquals(result, l);
    }

    @Test
    public void testchildren() throws CloneNotSupportedException {
        Board b = new Board("AB CD");
        List<Ilayout> result = b.children();
        List<Ilayout> l = new ArrayList<Ilayout>();
        l.add(new Board("A CDB"));
        l.add(new Board("A CD B"));
        l.add(new Board("ABD C"));
        l.add(new Board("AB D C"));
        //l.add(new Board("AB C"));
        assertEquals(result, l);
    }

    @Test
    public void testchildren2() throws CloneNotSupportedException {
        Board b=new Board("ABC");
        List<Ilayout> children = b.children();
        List<Ilayout> child=new ArrayList<Ilayout>();
        child.add(new Board("AB C"));
        assertEquals(child, children);
    }

    @Test
    public void testchildren4() throws CloneNotSupportedException {
        Board b=new Board("A BC");
        List<Ilayout> children = b.children();
        List<Ilayout> child=new ArrayList<Ilayout>();
        child.add(new Board("BCA"));child.add(new Board("AC B"));child.add(new Board("A B C"));
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

    @Test
    public void testgetH3() throws CloneNotSupportedException {
        Ilayout b=new Board("BAD FEC");
        Ilayout b2Board=new Board("ABCDEF");
        assertEquals(7, b.getH(b2Board),0);
    }

    @Test
    public void testgetH4() throws CloneNotSupportedException {
        Ilayout b=new Board("GF EDC B A");
        Ilayout b2Board=new Board("ABCDEFG");
        assertEquals(6, b.getH(b2Board),0);
    }

    @Test
    public void testgetH5() throws CloneNotSupportedException {
        Ilayout b=new Board("FACE BG D");
        Ilayout b2Board=new Board("E ACB GD F");
        assertEquals(7, b.getH(b2Board),0);
    }

    @Test
    public void testgetH6() throws CloneNotSupportedException {
        Ilayout b=new Board("BAD FEC");
        Ilayout b2Board=new Board("BADFEC");
        assertEquals(5, b.getH(b2Board),0);
    }

    @Test
    public void testgetH7() throws CloneNotSupportedException {
        Ilayout b=new Board("BAD FEC");
        Ilayout b2Board=new Board("ABDFEC");
        assertEquals(9, b.getH(b2Board),0);
    }

    @Test
    public void testgetH8() throws CloneNotSupportedException {
        Ilayout b=new Board("FA CE BG D");
        Ilayout b2Board=new Board("CFAE DBG");
        assertEquals(8, b.getH(b2Board),0);
    }

    @Test
    public void testgetH9() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCD");
        Ilayout b2Board=new Board("ABDC");
        assertEquals(4, b.getH(b2Board),0);
    }

    @Test
    public void testgetH10() throws CloneNotSupportedException {
        Ilayout b=new Board("BD A G C FE");
        Ilayout b2Board=new Board("ABC G F ED");
        assertEquals(4, b.getH(b2Board),0);
    }

    @Test
    public void testgetH11() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCDEF");
        Ilayout b2Board=new Board("ABDCEF");
        assertEquals(8, b.getH(b2Board),0);
    }

    @Test
    public void testgetH12() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCDEF");
        Ilayout b2Board=new Board("A B D C E F");
        assertEquals(5, b.getH(b2Board),0);
    }

    @Test
    public void testgetH13() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCDEFG");
        Ilayout b2Board=new Board("BCDEFGA");
        assertEquals(12, b.getH(b2Board),0);
    }

    @Test (expected = IllegalStateException.class)
    public void testgetH14() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCD EFGH IJ");
        Ilayout b2Board=new Board("DE AJC HGIFB");
        assertEquals(10, b.getH(b2Board),0);
    }

    @Test
    public void testgetG() throws CloneNotSupportedException {
        Ilayout b=new Board("AB C");
        Ilayout b2Board=new Board("A B C");
        double h=b.getH(b2Board);
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                assertEquals(true,h<=i.getG());
            }
        }
    }

    @Test
    public void testAdmissible() throws CloneNotSupportedException {
        Ilayout b=new Board("AB C");
        Ilayout b2Board=new Board("A B C");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible1() throws CloneNotSupportedException {
        Ilayout b=new Board("BAD FEC");
        Ilayout b2Board=new Board("ABCDEF");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            heuristcs.add(i.getH());
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible2() throws CloneNotSupportedException {
        Ilayout b=new Board("GF EDC B A");
        Ilayout b2Board=new Board("ABCDEFG");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible3() throws CloneNotSupportedException {
        Ilayout b=new Board("FACE BG D");
        Ilayout b2Board=new Board("E ACB GD F");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible4() throws CloneNotSupportedException {
        Ilayout b=new Board("BAD FEC");
        Ilayout b2Board=new Board("BADFEC");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
                Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible5() throws CloneNotSupportedException {
        Ilayout b=new Board("BAD FEC");
        Ilayout b2Board=new Board("ABDFEC");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible6() throws CloneNotSupportedException {
        Ilayout b=new Board("FA CE BG D");
        Ilayout b2Board=new Board("CFAE DBG");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible7() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCD");
        Ilayout b2Board=new Board("ABDC");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible8() throws CloneNotSupportedException {
        Ilayout b=new Board("BD A G C FE");
        Ilayout b2Board=new Board("ABC G F ED");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible9() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCDEF");
        Ilayout b2Board=new Board("ABDCEF");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible10() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCDEF");
        Ilayout b2Board=new Board("A B D C E F");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test
    public void testAdmissible11() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCDEFG");
        Ilayout b2Board=new Board("BCDEFGA");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testAdmissible12() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCD EFGH IJ");
        Ilayout b2Board=new Board("DE AJC HGIFB");
        double g=0;
        BestFirst s = new BestFirst();
        List<Double> heuristcs=new ArrayList<>();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                g=i.getG();
            }
        }
        for(Double h:heuristcs){
            assertEquals(true, h<=g);
        }
    }

    @Test (expected = IllegalStateException.class)
    public void test10() throws CloneNotSupportedException {
        Ilayout b=new Board("ABCD EFGH IJ");
        Ilayout b2Board=new Board("DE AJC HGIFB");

        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b,b2Board);
        while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()){
                assertEquals(11, (int)i.getG());
            }
        }
    }
}*/