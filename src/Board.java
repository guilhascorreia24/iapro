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
    public boolean equals(Object b){ //https://www.geeksforgeeks.org/inplace-rotate-square-matrix-by-90-degrees/
        Board b2=(Board)b;
        System.out.println(b2);
        int verify=0;
        b2= (Board) b2.clone();
        for (int x = 0; x < dim / 2; x++) { 
            for (int y = x; y < dim - x - 1; y++) { 
                char temp = b2.board[x][y]; 
  
                // Move values from right to top 
                b2.board[x][y] = b2.board[y][dim - 1 - x]; 
  
                // Move values from bottom to right 
                b2.board[y][dim - 1 - x] = b2.board[dim - 1 - x][dim - 1 - y]; 
  
                // Move values from left to bottom 
                b2.board[dim - 1 - x][dim - 1 - y] =b2.board[dim - 1 - y][x]; 
  
                // Assign temp to left 
                b2.board[dim - 1 - y][x] = temp; 
                System.out.println(b2+"\n"+board[x][y]+" "+b2.board[x][y]);

                if(b2.board[x][y]==board[x][y]){
                   // System.out.println(b2.board[x][y]+" "+board[x][y]);
                    verify++;
                }
                char temp2 = b2.board[x][y]; 
  
                // Move values from right to top 
                b2.board[x][y] = b2.board[y][dim - 1 - x]; 
  
                // Move values from bottom to right 
                b2.board[y][dim - 1 - x] = b2.board[dim - 1 - x][dim - 1 - y]; 
  
                // Move values from left to bottom 
                b2.board[dim - 1 - x][dim - 1 - y] =b2.board[dim - 1 - y][x]; 
  
                // Assign temp to left 
                b2.board[dim - 1 - y][x] = temp2;
                System.out.println(b2+"\n"+board[x][y]+" "+b2.board[x][y]);
                if(b2.board[x][y]==board[x][y]){
                   // System.out.println(b2.board[x][y]+" "+board[x][y]);
                    verify++;
                }
                char temp3 = b2.board[x][y]; 
  
                // Move values from right to top 
                b2.board[x][y] = b2.board[y][dim - 1 - x]; 
  
                // Move values from bottom to right 
                b2.board[y][dim - 1 - x] = b2.board[dim - 1 - x][dim - 1 - y]; 
  
                // Move values from left to bottom 
                b2.board[dim - 1 - x][dim - 1 - y] =b2.board[dim - 1 - y][x]; 
  
                // Assign temp to left 
                b2.board[dim - 1 - y][x] = temp3;
                System.out.println(b2+"\n"+board[x][y]+" "+b2.board[x][y]);
                if(b2.board[x][y]==board[x][y]){
                    //System.out.println(b2.board[x][y]+" "+board[x][y]);
                    verify++;
                }
                char temp4 = b2.board[x][y]; 
  
                // Move values from right to top 
                b2.board[x][y] = b2.board[y][dim - 1 - x]; 
  
                // Move values from bottom to right 
                b2.board[y][dim - 1 - x] = b2.board[dim - 1 - x][dim - 1 - y]; 
  
                // Move values from left to bottom 
                b2.board[dim - 1 - x][dim - 1 - y] =b2.board[dim - 1 - y][x]; 
  
                // Assign temp to left 
                b2.board[dim - 1 - y][x] = temp4;
                System.out.println(b2+"\n"+board[x][y]+" "+b2.board[x][y]+"\n-------------");
                if(b2.board[x][y]==board[x][y]){
                    // System.out.println(b2.board[x][y]+" "+board[x][y]);
                     verify++;
                 }
            } 
        }
        System.out.println(verify);
        if(verify==(dim/2)*(dim-(dim/2)-1)) return true;
        return false;
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