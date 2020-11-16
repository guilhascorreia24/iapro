import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
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
    //private int dim;
    private LinkedList<Stack<Character>> board=new LinkedList<Stack<Character>>();;

    public Board() {
    }

    public Board(String str) throws IllegalStateException {
        String[] b2= str.split(" ");
        int blocks=0;
       // System.out.println(b2.length);
        for(int i=0;i<b2.length;i++){
            if(!b2[i].equals("")) {   
                int j=0;
                Stack<Character> line=new Stack<Character>();
                while(j<b2[i].split("").length){
                    line.add(b2[i].charAt(j));
                    j++;
                }
                board.add(line);
            blocks+=j;}
        }
        if(blocks!=3){
            throw new IllegalStateException("Invalid arg in Board constructor");
        }
       // dim=board.size();
        //System.out.println(board);
    }

    @Override
    public String toString() {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        for (int i = 0; i < board.size(); i++) {
            if(board.get(i).size()>0){   
                pw.print("[");
                pw.print(board.get(i).elementAt(0));
                int j=1;
                    while(j<board.get(i).size())
                    {
                        pw.print(", "+board.get(i).elementAt(j));
                        j++;
                    }
                // if(i<dim-1)
                pw.println("]");
            }
        }
        return writer.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board nBoard=new Board();
        for(int i=0;i<board.size();i++){
            int j=0;
            Stack<Character> line=new Stack<Character>();
            while(j<board.get(i).size()){
                line.add(board.get(i).elementAt(j));
                j++;
            }
            nBoard.board.add(line);
        }
        //nBoard.dim=nBoard.board.size();
        return nBoard;
    }

    
    /**
     * Criar todos os filhos da board actual
     */
    @Override
    public List<Ilayout> children() throws CloneNotSupportedException { // criar os filhos
        List<Ilayout> children = new ArrayList<Ilayout>();
        for (int i = 0; i < board.size(); i++) {
            Board child = (Board) clone();
            char block = child.board.get(i).pop();
            if (child.board.get(i).isEmpty())
                child.board.remove(i);

            for (int j = 0; j < child.board.size(); j++) {
                Board cloneChild = (Board) child.clone();
                cloneChild.board.get(j).push(block);
                if (!children.contains(cloneChild) && !cloneChild.equals(this)) {
                    children.add(cloneChild);
                }
            }

            Stack<Character> last = new Stack<Character>();
            last.push(block);
            child.board.add(last);
            if (!children.contains(child) && !child.equals(this)) {
                children.add(child);
            }
        }
        return children;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }


    @Override
    public boolean equals(Object b) { // compara 2 boards
        if(this.getClass().equals(b.getClass())){
            Board b1=(Board) b;
            //System.out.println(this);
            for(Stack<Character> x:board){
                //System.out.println(b1.board.contains(x));
                if(!b1.board.contains(x)){
                    return false;
                }
            }
            return true;
        } else {
        return false;}
    }

    @Override
    public double getG() { // custo
        return 1;
    }
}