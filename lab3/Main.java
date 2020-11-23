import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {   
        Scanner sc = new Scanner(System.in);
        long startTime = System.nanoTime();
        BestFirst s = new BestFirst();
        Board s1=new Board(sc.nextLine());
        Board s2=new Board(sc.nextLine());
        int it = s.Ida(s1, s2);
        System.out.println(it);
        sc.close();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration*0.000001  +"ms");
    }

}