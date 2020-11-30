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
     * @return true if the receiver equals the argument l; return false otherwise.
     */
    boolean isGoal(Ilayout l);

    int verifywinner();

    Ilayout insertnew(int x,int y);

}

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];
    private char character, counter;

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
        character='O';
        counter='X';
    }

    public String toString() {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != 0)
                    pw.print((char) (board[i][j]));
                else
                    pw.print("-");
                // System.out.println(board[i][j]);
            }
            // if(i<dim-1)
            pw.println();
        }
        pw.println(character);
        return writer.toString();
    }

    public Ilayout insertnew(int x,int y){
        Board clone=(Board)clone();
        clone.character=counter;
        clone.counter=character;
        clone.board[x][y]=clone.character;
        return clone;
    }

    @Override
    public Object clone() {
        Board b = new Board();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                b.board[i][j] = board[i][j];
            }
        }
        return b;
    }

    @Override
    public List<Ilayout> children() throws CloneNotSupportedException { // criar os filhos
        List<Ilayout> p = new ArrayList<Ilayout>();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) {
                    Board b = (Board) clone();
                    b.character = counter;
                    b.counter = character;
                    b.board[i][j] = (int) b.character;
                    p.add(b);
                }
            }
        }
        // System.out.println(p);
        return p;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    @Override
    public boolean equals(Object b) { // compara 2 boards
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
    public int verifywinner() {
        boolean empty_spaces=false;
        //System.out.println(character+" "+counter);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][0] == character && board[i][1] == character && board[i][2] == character)
                    return 1;
                else if (board[0][j] == character && board[1][j] == character && board[2][j] == character)
                    return 1;
                else if (board[i][0] == counter && board[i][1] == counter && board[i][2] == counter)
                    return -1;
                else if (board[0][j] == counter && board[1][j] == counter && board[2][j] == counter)
                    return -1;
                if(board[i][j]==0){
                    empty_spaces=true;
                }
            }
        }
        if (board[0][0] == character && board[1][1] == character && board[2][2] == character)
            return 1;
        else if (board[0][2] == character && board[1][1] == character && board[2][0] == character)
            return 1;
        else if (board[0][0] == counter && board[1][1] == counter && board[2][2] == counter)
            return -1;
        else if (board[0][2] == counter && board[1][1] == counter && board[2][0] == counter)
            return -1;
        if(empty_spaces) return -2;
        return 0;

    }


}