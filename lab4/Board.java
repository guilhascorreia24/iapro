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

    int verifywinner(Ilayout b);
    
    int stateBoard();

    Ilayout insertnew(int x, int y);

    char getplayer();

}

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private char board[][];
    private char character, counter;

    public Board() {
        board = new char[dim][dim];
    }

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new char[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = str.charAt(si++);
        character = 'O';
        counter = 'X';
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
        // pw.println(character);
        return writer.toString();
    }

    public Ilayout insertnew(int x, int y) {
        Board clone = (Board) clone();
        clone.character = counter;
        clone.counter = character;
        clone.board[x][y] = clone.character;
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

    public Board rotate() {
        int x = 0, y = 0;
        char temp = board[x][y];
        char temp_1 = board[x][(y + 1)];
        // Move values from right to top
        board[x][y] = board[y][dim - 1 - x];
        board[x][(y + 1)] = board[(y + 1)][dim - 1 - x];
        // Move values from bottom to right
        board[y][dim - 1 - x] = board[dim - 1 - x][dim - 1 - y];
        board[(y + 1)][dim - 1 - x] = board[dim - 1 - x][dim - 1 - ((y + 1))];
        // Move values from left to bottom
        board[dim - 1 - x][dim - 1 - y] = board[dim - 1 - y][x];
        board[dim - 1 - x][dim - 1 - ((y + 1))] =board[dim - 1 - ((y + 1))][x];
        // Assign temp to left
        board[dim - 1 - y][x] = temp;
        board[dim - 1 - ((y + 1))][x] = temp_1;
        return this;
    }

    @Override
    public boolean equals(Object b) { // https://www.geeksforgeeks.org/inplace-rotate-square-matrix-by-90-degrees/
        Board b2 = (Board) b;
        b2= (Board) b2.clone();
        int v=0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                //System.out.println(b2.board[i][j]+" "+board[i][j]);
                if (b2.board[i][j] == board[i][j]) {
                    v++;
                }
            }
        }
        if (v == dim * dim)
        return true;
        // System.out.println(b2);
        b2=b2.rotate();
        // System.out.println(b2 + "\n" + board[x][y] + " " + b2.board[x][y]);
         v = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                //System.out.println(b2.board[i][j]+" "+board[i][j]);
                if (b2.board[i][j] == board[i][j]) {
                    v++;
                }
            }
        }
        //System.out.println(v);
        if (v == dim * dim)
            return true;
        // System.out.println(b2);
        b2=b2.rotate();
        // System.out.println(b2 + "\n" + board[x][y] + " " + b2.board[x][y]);

        v = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                //System.out.println(b2.board[i][j]+" "+board[i][j]);
                if (b2.board[i][j] == board[i][j]) {
                    v++;
                }
            }
        }
        //System.out.println(v);
        if (v == dim * dim)
            return true;
        // System.out.println(b2);
        b2=b2.rotate();
        // System.out.println(b2 + "\n" + board[x][y] + " " + b2.board[x][y]);

        v = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                //System.out.println(b2.board[i][j]+" "+board[i][j]);
                if (b2.board[i][j] == board[i][j]) {
                    v++;
                }
            }
        }
        //System.out.println(v);
        if (v == dim * dim)
            return true;
        // System.out.println(b2);
        return false;
    }

    /**
     * return 1 - winner 1 return -1 - winner 2 return -2 - continue return 0 - draw
     */
    @Override
    public int verifywinner(Ilayout b) {
        Board b2=(Board)b;
        boolean empty_spaces = false;
        for (int i = 0; i < dim; i++) {
            if (b2.board[i][0] == character && b2.board[i][1] == character && b2.board[i][2] == character)
                return 1;
            else if (b2.board[i][0] == counter && b2.board[i][1] == counter && b2.board[i][2] == counter)
                return -1;
            else if (b2.board[0][i] == character && b2.board[1][i] == character && b2.board[2][i] == character)
                return 1;
            else if (b2.board[0][i] == counter && b2.board[1][i] == counter && b2.board[2][i] == counter)
                return -1;
        }
        if (b2.board[0][0] == character && b2.board[1][1] == character && b2.board[2][2] == character)
            return 1;
        else if (b2.board[0][2] == character && b2.board[1][1] == character && b2.board[2][0] == character)
            return 1;
        else if (b2.board[0][0] == counter && b2.board[1][1] == counter && b2.board[2][2] == counter)
            return -1;
        else if (b2.board[0][2] == counter && b2.board[1][1] == counter && b2.board[2][0] == counter)
            return -1;

        pause: for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++)
                if (b2.board[i][j] == '-') {
                    empty_spaces = true;
                    break pause;
                }
        }
        if (empty_spaces)
            return -2;
        return 0;
    }

    @Override
    public int stateBoard(){
        return verifywinner(this);
    }

    @Override
    public char getplayer() {
        return character;
    }

    

}