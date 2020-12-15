
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int i=0,res=0;
        while(i<100){
            MCTS s = new MCTS();
            Board b=new Board("---------");
            //System.out.print("player y/n:\n");
            String c = "n";//sc.next();
            float start=System.nanoTime();
            while(!s.end_game){
                if(c.equals("y")){
                    b= (Board) b.insertnew(sc.nextInt(), sc.nextInt());
                }
                b=s.BestNextMove(b);
            }
            System.out.println(b); //+" move ["++"]"
            //System.out.println("-----------------");
            if(b.stateBoard()==0){
                res++;
            }
            i++;
        }   
        //float end=System.nanoTime()-start;
        System.out.println(res+"/"+i);
        sc.close();
    }
}