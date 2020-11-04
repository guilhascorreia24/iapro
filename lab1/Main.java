import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long startTime = System.nanoTime();
        Scanner sc = new Scanner(System.in);
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.IDs(new Board(sc.nextLine()), new Board(sc.nextLine()));
        if (it == null)
            System.out.println("no solution was found");
        else {
            while (it.hasNext()) {
                BestFirst.State i = it.next();
                System.out.println(i);
                if (!it.hasNext())
                    System.out.println((int)i.getG());
            }
        }
        sc.close();
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(actualMemUsed+" "+duration*0.000001+"ms");
    }
}