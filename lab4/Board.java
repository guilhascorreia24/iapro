import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

interface Ilayout {
    /**
     * @return os filhos do receptor.
     * @throws CloneNotSupportedException
     */
    List<Ilayout> children() throws CloneNotSupportedException;

    /**
     * 
     * @param b
     * @return
     */
    double verifywinner(Ilayout b);

    /**
     * 
     */
    double stateBoard();

    /**
     * 
     * @param x
     * @param y
     * @return
     * @throws CloneNotSupportedException
     */
    Ilayout insertnew(int x, int y) throws CloneNotSupportedException;


    /**
     * 
     * @return
     */
    char getplayer();

}

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private char board[][];
    public char character, counter;
    private double WIN=1,LOST=0,DRAW=0.99999;

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new char[dim][dim];
        int x=0,o=0;
        int si = 0;
        for (int i = 0; i < dim; i++){
            for (int j = 0; j < dim; j++){
                board[i][j] = str.charAt(si++);
                if(board[i][j]=='X') x++;
                else if(board[i][j]=='O') o++;
            }
        }
        if(x>o){
            character='X';
            counter='O';
        }else{
            character = 'O';
            counter = 'X';
        }
    }

    public String toString() {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                pw.print(board[i][j]);
            }
            pw.println();
        }
         pw.println(character);
        return writer.toString();
    }
    
    @Override
    public Ilayout insertnew(int x, int y) throws CloneNotSupportedException {
        Board clone = (Board) clone();
        if(board[x][y]!='-'){
            throw new IllegalAccessError("occupied position");
        }
        clone.character = counter;
        clone.counter = character;
        clone.board[x][y] = clone.character;
        return clone;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board b = (Board) super.clone();
        b.board=new char[dim][dim];
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
                if (board[i][j] == '-') {
                    Board b = (Board) clone();
                    b.character = counter;
                    b.counter = character;
                    b.board[i][j] = b.character;
                    if (!p.contains(b))
                        p.add(b);
                }
            }
        }
        return p;
    }

    @Override
    public boolean equals(Object b) {
        Board b2 = (Board) b;
      // System.out.println(b2+"\n"+this);
        int p = 0;
        boolean s=true,s1=true,s2=true,s3=true;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == b2.board[i][j] ) {
                    p++;
                }
                if (board[i][j] != b2.board[i][(dim - 1) - j]) {
                    s=false;
                }
                if (board[i][j] != b2.board[(dim - 1) - i][j]) {
                    s1=false;
                }
                if (board[i][j] != b2.board[j][i]) {
                    s2=false;
                }
                if (board[i][j] != b2.board[(dim-1) - j][(dim-1) - i]) {
                    s3= false;
                }
            }
        }
        if (p == dim * dim) { 
            return true;
        }
       //System.out.println(s+" "+s1+" "+s2+" "+s3+" "+p);
        if(!s && !s1 && !s2 && !s3){
            return false;
        }
        return true;
    }

    /**
     * return 1 - winner 1 return -1 - winner 2 return -2 - continue return 0 - draw
     */
    @Override
    public double verifywinner(Ilayout b) {
        Board b2 = (Board) b;
        boolean empty_spaces = false;
        for (int i = 0; i < dim; i++) {
            if (b2.board[i][0] == character && b2.board[i][1] == character && b2.board[i][2] == character)
                return WIN;
            else if (b2.board[i][0] == counter && b2.board[i][1] == counter && b2.board[i][2] == counter)
                return LOST;
            else if (b2.board[0][i] == character && b2.board[1][i] == character && b2.board[2][i] == character)
                return WIN;
            else if (b2.board[0][i] == counter && b2.board[1][i] == counter && b2.board[2][i] == counter)
                return LOST;
        }
        if (b2.board[0][0] == character && b2.board[1][1] == character && b2.board[2][2] == character)
            return WIN;
        else if (b2.board[0][2] == character && b2.board[1][1] == character && b2.board[2][0] == character)
            return WIN;
        else if (b2.board[0][0] == counter && b2.board[1][1] == counter && b2.board[2][2] == counter)
            return LOST;
        else if (b2.board[0][2] == counter && b2.board[1][1] == counter && b2.board[2][0] == counter)
            return LOST;

        pause: for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++)
                if (b2.board[i][j] == '-') {
                    empty_spaces = true;
                    break pause;
                }
        }
        if (empty_spaces)
            return -2;
        return DRAW;
    }

    @Override
    public double stateBoard() {
        return verifywinner(this);
    }

    @Override
    public char getplayer() {
        return character;
    }


}