

import java.util.Set;

/* A demo reactive agent that plays randomly */
public class DEMOAgent {
	/**
     * return a valid random move.
     * @param board         the Tic Tac Toe board to play on
     */
	public static int move(Board b) {
		Set<Integer> moves = b.getAvailableMoves();
		
		int nam = moves.size();
		Integer[] array = new Integer[nam];
	    moves.toArray(array);
		
		int r = (int)(Math.random() * nam);
		return array[r];
	}
}
