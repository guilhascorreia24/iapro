import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        BestFirst s = new BestFirst();
        Board play = new Board("000000000");
        int pc=0;
        while (!play.full_board()) {
            System.out.println(play);
            System.out.print("x:");
            int x=sc.nextInt();
            System.out.print("y:");
            int y=sc.nextInt();
            System.out.println();
            play.new_value(x, y);
            System.out.println(play);
            pc++;
            play = (Board) s.mcts(play, pc % 2);
            //System.out.println(play);
            //if(play.winningVerification()==1 || play.winningVerification()==0) break;
            pc++;
        }
        play.your_simbol(pc);
        if(play.winningVerification()==1){
            System.out.println("win");
        }else{
            System.out.println("lost");
        }
        sc.close();
    }
}