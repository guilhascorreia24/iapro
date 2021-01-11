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
     * @param b representa o estado da board
     * @return o valor associado ao estado da board atual
     */
    double verifywinner(Ilayout b);

    /**
     * 
     * @param x representa uma coordenada
     * @param y representa uma coordenada
     * @return o estado da board com a jogada feita
     * @throws CloneNotSupportedException
     */
    Ilayout insertnew(int x) throws CloneNotSupportedException;

    /**
     * @return o valor associado ao estado da board atual
     */
    double stateBoard();

    /**
     * @return o simbolo do jogador
     */
    char getplayer();

    /**
     * Pontuacao,  
     */
    double WIN=1,LOST=0,DRAW=0.5;

    /**
     * 
     * @return posicao da jogada na tabela
     */
    int getPosition();

}

public class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private char board[][];
    private char character, counter;
    private int position;

    /**
     * Cria a board apartir de uma string
     * @param str representa uma String
     * @inv str tem que ter tamanho 9
     * @throws IllegalStateException se @inv for violada
     */
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

    /**
     * @return representação da board
     */
    public String toString() {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                pw.print(board[i][j]);
            }
            pw.println();
        }
         //pw.println(counter);
        return writer.toString();
    }
    
    /**
     * @param position representa a posiçao na board
     * @return da nova board com a jogada feita pelo jogador  
     */
    @Override
    public Ilayout insertnew(int position) throws CloneNotSupportedException {
        Board clone = (Board) clone();
        int x=(int)(position/dim);
        int y=(position%dim);
        //System.out.println(x+" "+y);
        if(board[x][y]!='-'){
            throw new IllegalAccessError("occupied position");
        }
        clone.character = counter;
        clone.counter = character;
        clone.board[x][y] = clone.character;
        return clone;
    }

    /**
     * @return um clone da board atual
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Board clone = (Board) super.clone();
        clone.board=new char[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                clone.board[i][j] = board[i][j];
            }
        }
        return clone;
    }

    /**
     * Cria boards filhas partir da original
     * @return uma lista com os filhos da board 
     */
    @Override
    public List<Ilayout> children() throws CloneNotSupportedException { 
        List<Ilayout> p = new ArrayList<Ilayout>();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == '-') {
                    Board b = (Board) clone();
                    b.character = counter;
                    b.counter = character;
                    b.position=(i*dim)+j;
                    b.board[i][j] = b.character;
                    if (!p.contains(b))
                        p.add(b);
                }
            }
        }
        return p;
    }

    /**
     * Comparação se 2 boards são iguais, usando simetrias
     * @param o objecto a comparar
     * @return boolean, true se as boards são iguais, false se as boards são diferentes
     * 
     */
    @Override
    public boolean equals(Object o) {
        Board board2 = (Board) o;
      // System.out.println(board2+"\n"+this);
        int p = 0;
        boolean s=true,s1=true,s2=true,s3=true;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == board2.board[i][j] ) {
                    p++;
                }
                if (board[i][j] != board2.board[i][(dim - 1) - j]) {
                    s=false;
                }
                if (board[i][j] != board2.board[(dim - 1) - i][j]) {
                    s1=false;
                }
                if (board[i][j] != board2.board[j][i]) {
                    s2=false;
                }
                if (board[i][j] != board2.board[(dim-1) - j][(dim-1) - i]) {
                    s3= false;
                }
            }
        }
        if (p == dim * dim) { 
            return true;
        }
        if(!s && !s1 && !s2 && !s3){
            return false;
        }
        return true;
    }

    /**
     * @param layout representa o estado a board atual
     * Verificação se existe algum vencedor ou empate
     * se não houve o jogo continua
     * @return 1 - winner 1 ,0 - winner 2,-2 - continue ,0.5 - draw
     */
    @Override
    public double verifywinner(Ilayout layout) {
        Board board2 = (Board) layout;
        boolean empty_spaces = false;
        for (int i = 0; i < dim; i++) {
            if (board2.board[i][0] == character && board2.board[i][1] == character && board2.board[i][2] == character)
                return WIN;
            else if (board2.board[i][0] == counter && board2.board[i][1] == counter && board2.board[i][2] == counter)
                return LOST;
            else if (board2.board[0][i] == character && board2.board[1][i] == character && board2.board[2][i] == character)
                return WIN;
            else if (board2.board[0][i] == counter && board2.board[1][i] == counter && board2.board[2][i] == counter)
                return LOST;
        }
        if (board2.board[0][0] == character && board2.board[1][1] == character && board2.board[2][2] == character)
            return WIN;
        else if (board2.board[0][2] == character && board2.board[1][1] == character && board2.board[2][0] == character)
            return WIN;
        else if (board2.board[0][0] == counter && board2.board[1][1] == counter && board2.board[2][2] == counter)
            return LOST;
        else if (board2.board[0][2] == counter && board2.board[1][1] == counter && board2.board[2][0] == counter)
            return LOST;

        pause: for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++)
                if (board2.board[i][j] == '-') {
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

    @Override
    public int getPosition() {
        return position;
    }


}