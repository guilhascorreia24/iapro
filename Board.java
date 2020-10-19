import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    private int dim;
    private List<Stack<Character>> board=new ArrayList<Stack<Character>>();;

    public Board() {
    }

    public Board(String str) throws IllegalStateException {
        String[] b2= str.split(" ");
        dim=board.size();
        for(int i=0;i<b2.length;i++){
            //System.out.println(b2[i].split("").length);
            int j=0;
            Stack<Character> line=new Stack<Character>();
            while(j<b2[i].split("").length){
                line.add(b2[i].charAt(j));
                j++;
            }
            board.add(line);
        }
        System.out.println(board);
    }

    public String toString() {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        for (int i = 0; i < dim; i++) {
            pw.print("[");
            pw.print(board.get(i).pop());
            int j=0;
            while(j<board.get(i).size())
            {
                pw.print(", "+board.get(i).elementAt(j++));
            }
            // if(i<dim-1)
            pw.println("]");
        }
        // System.out.println();
        return writer.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board nBoard=new Board();
        for(int i=0;i<dim;i++){
            int j=0;
            Stack<Character> line=new Stack<Character>();
            while(j<board.get(i).size()){
                line.add(board.get(i).elementAt(j++));
            }
            nBoard.board.add(line);
        }
        nBoard.dim=nBoard.board.size();
        return nBoard;
    }

    @Override
    public List<Ilayout> children() throws CloneNotSupportedException { // criar os filhos
        List<Ilayout> childs=new ArrayList<Ilayout>();
        System.out.println(this.toString());
        for(Stack<Character> x:board){
            //System.out.println(x);
            Board child=(Board) clone();
            //System.out.println(child);
            Character el=x.pop();
            if(x.isEmpty()) child.board.remove(x);
            
            for(int j=0;j<child.dim;j++){
                Board child2=(Board) child.clone();
                child2.board.get(j).push(el);
                childs.add(child2);
            }
            Stack<Character> line=new Stack<Character>();
            line.push(el);
            child.board.add(line);
            childs.add(child);
        }
        System.out.println(childs);
        return childs;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }


    @Override
    public boolean equals(Object b) { // compara 2 boards
        if(this.getClass().equals(b.getClass())){
            Board b1=(Board) b;
            for(Stack<Character> x:board){
                if(!b1.board.contains(x)){
                    return false;
                }
            }

        }
        return false;
    }

    @Override
    public double getG() { // custo
        return 1;
    }
}