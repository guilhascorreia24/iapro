
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        BestFirst s = new BestFirst();
        Board b=new Board("---------");
        System.out.print("player y/n:");
        String c = sc.nextLine();
        long startTime = System.nanoTime();
        while(!s.end_game){
            //System.out.println(c.equals("y"));
            if(c.equals("y"))
                b= (Board) b.insertnew(sc.nextInt(), sc.nextInt());
            b=s.BestNextMove(b);
            System.out.println(b+"\n"+s.winner); //+" move ["++"]"
            System.out.println("-----------------");

        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration*0.000001  +"ms");
        sc.close();
    }
}