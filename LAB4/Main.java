import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        BestFirst s = new BestFirst();
        BestFirst.State play = new BestFirst.State(new Board("000000000"),null);
        while (!s.end_game) {
            play = s.mcts(play);
            //System.out.println(play);
            //System.out.println(play.w+"  "+play.n);
        }
        sc.close();
    }
}