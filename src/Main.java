
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        BestFirst s = new BestFirst();
        Board b=new Board("000000000");
        while(!s.end_game){
            b= (Board) b.insertnew(sc.nextInt(), sc.nextInt());
            b=s.BestNextMove(b);
            System.out.println(b+"\n"+s.winner); //+" move ["++"]"
            System.out.println("-----------------");

        }
        sc.close();
    }
}