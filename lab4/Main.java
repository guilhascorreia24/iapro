
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public  void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        MCTS s = new MCTS();
        Board b = new Board("---------");
        System.out.print("play 1/0?");
        int t=sc.nextInt();
        if(t==0){
            long startTime = System.nanoTime();
            Iterator<MCTS.State> it = s.solve(b).iterator();
            if (it == null)
                System.out.println("no solution was found");
            else {
                while (it.hasNext()) {
                    MCTS.State i = it.next();
                    System.out.println(i);
                }
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println((duration/1000000)*0.001  +"s");
            }
        }
        else{
            while(!s.end_game){
                b = (Board) b.insertnew(sc.nextInt());
                b= (Board) s.BestNextMove(b).layout;

                System.out.println(b);
            }
        }
        sc.close();
    }

}