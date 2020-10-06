import java.util.ArrayList;
import java.util.List;

interface Ilayout {
    /**
     * @return the children of the receiver.
     * @throws CloneNotSupportedException
     */
    List<Ilayout> children() throws CloneNotSupportedException;

    /**
     * @return true if the receiver equals the argument l; return false otherwise.
     */
    boolean isGoal(Ilayout l);

    /**
     * @return the cost for moving from the input config to the receiver.
     */
    double getG();

}

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];

    public Board() {
        board = new int[dim][dim];
    }

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
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != 0)
                    r.append(board[i][j]);
                else
                    r.append(" ");
                // System.out.println(board[i][j]);
            }
            r.append("\n");
        }
        return r.toString();
    }

    @Override
    public Object clone(){
        Board b=new Board();
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                b.board[i][j]=board[i][j];
            }
        }
        return b;
    }

    @Override
    public List<Ilayout> children() throws CloneNotSupportedException {
        List<Ilayout> p = new ArrayList<Ilayout>();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    Board b = (Board) clone();
                    if(j+1<dim){
                        b.board[i][j]=board[i][j+1];
                        b.board[i][j+1]=0;
                        //System.out.println(b);
                        p.add(b);}
                    if(j-1>=0){
                        b=(Board) clone();
                        b.board[i][j]=board[i][j-1];
                        b.board[i][j-1]=0;
                        p.add(b);
                        //System.out.println(b);
                    }
                    if(i+1<dim){
                        b=(Board) clone();
                        b.board[i][j]=board[i+1][j];
                        b.board[i+1][j]=0;
                        p.add(b);
                    }
                    if(i-1>=0){
                        b=(Board) clone();
                        b.board[i][j]=board[i-1][j];
                        b.board[i-1][j]=0;
                        p.add(b);
                    }
                }
            }
        }
        //System.out.println(p);
        return p;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    @Override
    public boolean equals(Object b) {
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
    public double getG() {
        return 1;
    }
}