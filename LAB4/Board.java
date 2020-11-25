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
    
    boolean full_board();
    
    int winningVerification();

    void your_simbol(int pc);


}

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];
    public char character; // defenir depois para quanto for pc vs pc

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = Character.getNumericValue(str.charAt(si++));
        your_simbol(0);
    }

    public void new_value(int x,int y){
        if(x>=0 && x<dim && y>=0 && y<dim){
            if(board[x][y]==0)
                board[x][y]=character-55;
            else throw new IllegalStateException("Invalid new arg in Board"); 
        }else{
            throw new IllegalStateException("Invalid new arg in Board"); 
        }
    }


    @Override
    public void your_simbol(int pc){
        if(pc%2!=0)
            character='X';
        else character='O';
    }

    public int winningVerification(){
        for(int i = 0 ; i < dim; i++)
        {
            if(board[i][0] == (int) (character - 55) && board[i][1] == (int) (character - 55) && board[i][2] == (int) (character - 55)) //verifica 3 em linha na horizontal
                return 1;
            else if(board[0][i] == (int) (character - 55) && board[1][i] == (int) (character - 55) && board[2][i] == (int) (character - 55)) //verifica 3 em linha na vertical
                return 1;
        }
        if(board[0][0] == (int) (character - 55) && board[1][1] == (int) (character - 55) && board[2][2] == (int) (character - 55)) //verifica 3 em linha na diagonal
            return 1;
        else if(board[0][2] == (int) (character - 55) && board[1][1] == (int) (character - 55) && board[2][0] == (int) (character - 55)) //verifica 3 em linha na outra diagonal
            return 1;
        return 0;
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
                    child.board[i][j]=(int)(character-55);
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

    public boolean full_board(){
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                if(board[i][j]==0)
                    return false;
            }
        }
        return true;
    }

}