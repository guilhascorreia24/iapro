import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Board b = new Board();

        while (!b.isGameOver()) {
            if (b.getTurn() != Board.State.X){
                int x=sc.nextInt();
                b.move(x);
                //System.out.println("O move:"+x);
            }
            else {
                int o=IA2020_21P01G05Agent.move(b);
                b.move(IA2020_21P01G05Agent.move(b));
                //System.out.println("X move:"+o);
            }
            System.out.println(b+"\n");
        }
    }
}