import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Board b = new Board();
        MCTS mcts = new MCTS();

        while (!b.isGameOver()) {
            if (b.getTurn() == Board.State.X){
                b.move(sc.nextInt());
            }
            else {
                int o=mcts.move(b);
                b.move(mcts.move(b));
                System.out.println(o);
            }
            //System.out.println(b);
        }
    }
}