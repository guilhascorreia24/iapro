import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {  
        long startTime = System.nanoTime(); 
        Scanner sc = new Scanner(System.in);
        //long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        BestFirst s = new BestFirst();
        Board s1=new Board(sc.nextLine());
        Board s2=new Board(sc.nextLine());
        //System.out.println(s1.blocks+" "+s2.blocks);
        Iterator<BestFirst.State> it = s.Ida(s1, s2);
        //System.out.println(s1.blocks+" "+s2.blocks);
        if (it == null)
            System.out.println("no solution was found");
        else {
            while (it.hasNext()) {
                BestFirst.State i = it.next();
                double g = i.getG();
                double h = i.getH();
                double f = i.getF();
                System.out.println("G: "+g + "    H: " + h + "    F: " + f);
                System.out.println(i);
                if(!it.hasNext()) System.out.println((int) i.getG());
                if (!it.hasNext())
                    System.out.println((int)i.getG());
            }
        }
        sc.close();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        //long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        //long actualMemUsed=afterUsedMem-beforeUsedMem;
        System.out.println(duration*0.000001  +"ms");
        //System.out.println(actualMemUsed);
    }

}