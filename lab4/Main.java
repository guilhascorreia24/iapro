
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
<<<<<<< HEAD
        System.out.println("test y/n?");
        String x = sc.next();
        if (x.equals("n")) {
            MCTS s = new MCTS();
            Board b = new Board("---------");
            System.out.print("player y/n:");
            String c = sc.next();
            String c1 = null;
            if (c.equals("y")) {
                System.out.print("O/X:");
                c1 = sc.next();
            }
            float start = System.nanoTime();
            while (!s.end_game) {
                if (c.equals("y") && c1.equals("X")) {
                    b = (Board) b.insertnew(sc.nextInt(), sc.nextInt());
                }
                b = s.BestNextMove(b);
                System.out.println(b); // +" move ["++"]"
                System.out.println("-----------------");
                if (c.equals("y") && c1.equals("O") && !s.end_game) {
                    b = (Board) b.insertnew(sc.nextInt(), sc.nextInt());
                }
            }
            float end = System.nanoTime() - start;
            System.out.println(end * Math.pow(10, -6));
        } else

        {
            int i = 0, res = 0;
            while (i < 1000) {
                MCTS s = new MCTS();
                Board b = new Board("---------");
                float start = System.nanoTime();
                while (!s.end_game) {
                    b = s.BestNextMove(b);
                }
                float end = System.nanoTime() - start;
                System.out.println(i + ":" + end * Math.pow(1, -6) + "\n" + b);
                if (b.stateBoard() == 0) {
                    res++;
=======
        MCTS s = new MCTS();
        Board b = new Board("---------");
        System.out.print("play 1/0?");
        int t=sc.nextInt();
        if(t==0){
            Iterator<MCTS.State> it = s.solve(b);
            if (it == null)
                System.out.println("no solution was found");
            else {
                while (it.hasNext()) {
                    MCTS.State i = it.next();
                    System.out.println(i);
                    if (!it.hasNext())
                        System.out.println(i.winner());
>>>>>>> f262fcdffeaab2e1832c202c24f7f5024f8e4a15
                }
            }}
        else{
            while(!s.end_game){
                b = (Board) b.insertnew(sc.nextInt(), sc.nextInt());
                b= (Board) s.BestNextMove(b).layout;
                System.out.println(b);
            }
        }
        sc.close();
    }

}