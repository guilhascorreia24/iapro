/*import org.junit.Test;

public class test {
    @Test
    public void testPrecision() throws CloneNotSupportedException {
        int i = 0, res = 0,j=0,p=0;
        while(j<10){
            i=0;res=0;
            while (i < 100) {
                BestFirst s = new BestFirst();
                Board b = new Board("---------");
                while (!s.end_game) {
                    b = (Board) s.BestNextMove(b);
                }
                if (b.verifywinner() != 0) {
                    res++;
                }
                i++;
            }
            p+=res;
            System.out.println(res + "/" + i);
            j++;
        }
        System.out.println((double)p/(double)(i*j));
    }
}*/
