import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        BestFirst s = new BestFirst();
        BestFirst.State play = new BestFirst.State(new Board("000000000"),null,0);
        while (!play.layout.full_board()) {
            System.out.print("x:");
            int x=sc.nextInt();
            System.out.print("y:");
            int y=sc.nextInt();
            System.out.println();
            Board b=(Board) play.layout.clone();
            b.new_value(x, y);
            play=new BestFirst.State(b,play,play.pc+1);
            System.out.println(play);
            play = s.mcts(play);
            System.out.println(play);
            play.layout.your_simbol(0);
            if(play.layout.winningVerification()==1){
                System.out.println("win");
                break;
            }else if(play.layout.winningVerification()==0){
                System.out.println("lost");
                break;
            }

        }
        sc.close();
    }
}