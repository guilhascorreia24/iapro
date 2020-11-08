import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {   
        Scanner sc = new Scanner(System.in);
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long startTime = System.nanoTime();
        BestFirst s = new BestFirst();
        Board s1=new Board(sc.nextLine());
        Board s2=new Board(sc.nextLine());
        if(s1.blocks!=s2.blocks){
            sc.close();
            throw new IllegalStateException("Fail: Boards sizes");
        }
        Iterator<BestFirst.State> it = s.Ida(s1, s2);
        if (it == null)
            System.out.println("no solution was found");
        else {
            while (it.hasNext()) {
                BestFirst.State i = it.next();
                //System.out.println();
                if (!it.hasNext())
                    System.out.println((int)i.getG());
            }
        }
        //System.out.println(s.nos);
        sc.close();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        System.out.println(duration*0.000001  +"ms");
        System.out.println(actualMemUsed);
    }

    /*public static void main (String [] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        Board start = new Board(sc.nextLine());
        Board goal = new Board(sc.nextLine());
        //System.out.println(start.getH(goal)); //n de blocos mal posiciionados entre start e goal
        if(start.blocks!=goal.blocks){
            sc.close();
            throw new IllegalStateException("Fail: Boards sizes");
        }
        BestFirst s = new BestFirst();
        //System.out.println(start.getH(goal));
        Iterator<BestFirst.State> it = s.solve(start, goal);
        if (it==null) System.out.println("no solution was found");
        else{

            while(it.hasNext()){
                BestFirst.State i = it.next();
                double g = i.getG();
                double h = i.getH();
                double f = i.getF();
                System.out.println("G: "+g + "    H: " + h + "    F: " + f);
                System.out.println(i);
                if(!it.hasNext()) System.out.println((int) i.getG());
            }
        }
        
        sc.close();
        }*/
}