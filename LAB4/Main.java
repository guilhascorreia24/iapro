import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws Exception {
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(new Board("000000000"));
        if (it == null)
            System.out.println("no solution was found");
        else {
            while (it.hasNext()) {
                BestFirst.State i = it.next();
                System.out.println(i);
                if (!it.hasNext())
                    System.out.println((int)i.getG());
            }
        }
    }
}