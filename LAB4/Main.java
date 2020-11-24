import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        BestFirst s = new BestFirst();
        Board play = new Board("000000000");
        while (!play.full_board()) {
            System.out.print("x:");
            int x=sc.nextInt();
            if(x>=3) 
                throw new IllegalStateException("Fail X");
            System.out.print("y:");
            int y=sc.nextInt();
            System.out.println();
            if(y>=3) 
                throw new IllegalStateException("Fail Y");
            if(play.board[y][x]!=0) 
                throw new IllegalStateException("Fail board");
            play.board[y][x]=(char)('X'-55);
            System.out.println(play);
            Iterator<BestFirst.State> it = s.mcts(play);

        }
        sc.close();
    }
}