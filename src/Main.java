
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        BestFirst s = new BestFirst();
        Board b=new Board("---------");
        System.out.print("player y/n:");
<<<<<<< HEAD
        String c = sc.nextLine();
        long startTime = System.nanoTime();
=======
        String c = "n";
        float start=System.nanoTime();
>>>>>>> 528d0af3a9e00437409d5bfaa7b0407bed7cf8e9
        while(!s.end_game){
            if(c.equals("y")){
                b= (Board) b.insertnew(sc.nextInt(), sc.nextInt());
            }
            b=s.BestNextMove(b);
            System.out.println(b); //+" move ["++"]"
            System.out.println("-----------------");
        }
<<<<<<< HEAD
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration*0.000001  +"ms");
=======
        float end=System.nanoTime()-start;
        System.out.println(end*Math.pow(10, -6));
>>>>>>> 528d0af3a9e00437409d5bfaa7b0407bed7cf8e9
        sc.close();
    }
}