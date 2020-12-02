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

    int verifywinner();

    Ilayout insertnew(int x,int y);


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
        //pw.println(character);
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
                if (board[i][j] == '-') {
                    Board b = (Board) clone();
                    b.character = counter;
                    b.counter = character;
                    b.board[i][j] = b.character;
                    if(!p.contains(b))
                        p.add(b);
                }
            }
        }
        return p;
    }

    @Override
    public boolean equals(Object b){
        Board b2 = (Board) b;
        Board b3= ((Board) b2.clone()).rotate();
        Board b4=((Board) b3.clone()).rotate();
        Board b5=((Board) b4.clone()).rotate();
        if(compare(b2) || compare(b3) || compare(b4) || compare(b5)){
            return true;
        }
        return false;
    }

    private boolean compare (Board b) { //compara 2 boards
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != b.board[i][j])
                    return false;
            }
        }
        return true;
    }

    public Board rotate(){ //https://www.geeksforgeeks.org/inplace-rotate-square-matrix-by-90-degrees/
        for (int x = 0; x < dim / 2; x++) { 
            for (int y = x; y < dim - x - 1; y++) { 
                char temp = board[x][y]; 
  
                // Move values from right to top 
                board[x][y] = board[y][dim - 1 - x]; 
  
                // Move values from bottom to right 
                board[y][dim - 1 - x] = board[dim - 1 - x][dim - 1 - y]; 
  
                // Move values from left to bottom 
                board[dim - 1 - x][dim - 1 - y] =board[dim - 1 - y][x]; 
  
                // Assign temp to left 
                board[dim - 1 - y][x] = temp; 
            } 
        } 
        return this;
    }

    /**
     * return 1 - winner 1
     * return -1 - winner 2
     * return -2 - continue
     * return 0 - draw
     */
    @Override
    public int verifywinner() {
        boolean empty_spaces = false;
        for (int i = 0; i < dim; i++) 
        {
        	if (board[i][0] == character && board[i][1] == character && board[i][2] == character)
                return 1;
            else if (board[i][0] == counter && board[i][1] == counter && board[i][2] == counter)
                return -1;
            else if (board[0][i] == character && board[1][i] == character && board[2][i] == character)
                return 1;
            else if (board[0][i] == counter && board[1][i] == counter && board[2][i] == counter)
                return -1;
        }
        if (board[0][0] == character && board[1][1] == character && board[2][2] == character)
            return 1;
        else if (board[0][2] == character && board[1][1] == character && board[2][0] == character)
            return 1;
        else if (board[0][0] == counter && board[1][1] == counter && board[2][2] == counter)
            return -1;
        else if (board[0][2] == counter && board[1][1] == counter && board[2][0] == counter)
            return -1;
        
        pause:
        for(int i = 0; i < dim; i++)
        {
        	for(int j = 0; j < dim; j++)
                if(board[i][j] == '-')
                {
                    empty_spaces = true;
                    break pause;
                }
        }
        if(empty_spaces) 
        	return -2;
        return 0;
    }


}