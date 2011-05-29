package quarto;

import agent.Action;



public class Quarto {

	QBoard board;
	Player p1;
	Player p2;
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 */
	public Quarto(Player p1, Player p2) {
		board = new QBoard();
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Quarto(Player p1, Player p2, QBoard board) {
		this.board = board;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public boolean isDraw() {
		return board.gameOver() && !board.isWinningBoard();
	}
	
	public boolean oneHasWon() {
		return !board.oneToMove() && board.isWinningBoard();
	}
	
	public boolean twoHasWon() {
		return board.oneToMove() && board.isWinningBoard();
	}
	
	public boolean gameOver() {
		return board.gameOver();
	}
	
	public String printBoard() {
		return board.toString();
	}
	
	public Player getNextPlayer() {
		if(board.oneToMove())
			return p1;
		else
			return p2;
	}
	
	public void step() {
		Player nextPlayer = getNextPlayer();
		//System.out.println(nextPlayer.getName() + " to move");
		QMove nextMove = (QMove)nextPlayer.getAction(board.getPercept());
		//System.out.println(nextPlayer.getName() + " making move: " + nextMove.toString());
		board.update(nextMove);
	}
	
}
