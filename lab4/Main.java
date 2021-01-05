
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public  static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        MCTS s = new MCTS();
        Board b = new Board("---------");
        System.out.print("Esolha uma opçao: player vs bot(1)/bot vs bot(0):");
        int t=sc.nextInt();
        if(t==0){
            Iterator<MCTS.State> it = s.solve(b).iterator();
            if (it == null)
                System.out.println("no solution was found");
            else {
                while (it.hasNext()) {
                    MCTS.State i = it.next();
                    System.out.println(i);
                }
            }
        }
        else{
            while(!s.end_game){
                System.out.print("Escolha uma posiçao:");
                b = (Board) b.insertnew(sc.nextInt());
                MCTS.State s1= s.BestNextMove(b);
                System.out.println(s1);
                b= (Board) s1.getBoard();
            }
        }
        sc.close();
    }

}