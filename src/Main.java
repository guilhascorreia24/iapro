import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {  
        Scanner sc = new Scanner(System.in);
        long startTime = System.nanoTime(); 
        //long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        BestFirst s = new BestFirst();
        Board s1=new Board(sc.nextLine());
        Board s2=new Board(sc.nextLine());
        /*if(s1.blocks!=s2.blocks){
            sc.close();
            throw new IllegalStateException("Fail: Boards sizes");
        }*/
        Iterator<BestFirst.State> it = s.mcts(s1, s2);
        if (it == null)
            System.out.println("no solution was found");
        else {
            while (it.hasNext()) {
                BestFirst.State i = it.next();
                if (!it.hasNext())
                    System.out.println(i.getG());
            }
        }
        sc.close();
    }

    

}