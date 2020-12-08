
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        MCTS s = new MCTS();
        Board b=new Board("---------");
        System.out.print("player y/n:");
        String c = sc.next();
        float start=System.nanoTime();
        while(!s.end_game){
            if(c.equals("y")){
                b= (Board) b.insertnew(sc.nextInt(), sc.nextInt());
            }
            b=s.BestNextMove(b);
            System.out.println(b); //+" move ["++"]"
            System.out.println("-----------------");
        }
        float end=System.nanoTime()-start;
        System.out.println(end*Math.pow(10, -6));
        sc.close();
    }
}