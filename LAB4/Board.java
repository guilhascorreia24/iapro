import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

interface Ilayout {
    /**
     * @return the children of the receiver.
     * @throws CloneNotSupportedException
     */
    List<Ilayout> children() throws CloneNotSupportedException;

    /**
     * @return the cost for moving from the input config to the receiver.
     */
    double getG();

}

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];


    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = Character.getNumericValue(str.charAt(si++));
    }

    public String toString() {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != 0)
                    pw.print((char)(board[i][j]+55));
                else
                    pw.print(" ");
                if(j<dim-1){
                    pw.print("|");
                }
                // System.out.println(board[i][j]);
            }
            pw.println();
            if(i<dim-1)
                pw.println("------");
        }
        //System.out.println();
        return writer.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board b= (Board) super.clone();
        b.board=new int[dim][dim];
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                b.board[i][j]=board[i][j];
            }
        }
        return b;
    }

    @Override
    public List<Ilayout> children() throws CloneNotSupportedException { // criar os filhos 
        List<Ilayout> p = new ArrayList<Ilayout>();
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                if(board[i][j]==0){
                    Board child=(Board)clone();
                    child.board[i][j]=(int)'X';
                    p.add(child);
                }
            }
        }
        //System.out.println(p);
        return p;
    }

    @Override
    public boolean equals(Object b) { //compara 2 boards
        Board b2 = (Board) b;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != b2.board[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public double getG() { // custo
        return 1;
    }
}