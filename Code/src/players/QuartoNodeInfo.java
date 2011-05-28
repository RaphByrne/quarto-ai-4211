package players;

import java.awt.Point;

import search.Node;
import search.NodeInfo;
import quarto.*;

public class QuartoNodeInfo implements NodeInfo {

	boolean isOne;
	double depthLim;
	
	public QuartoNodeInfo(boolean isOne) {
		this.isOne = isOne;
		depthLim = 4;
	}
	
	@Override
	public boolean isGoal(Node node) {
		QBoard board  = (QBoard)node.getState();
		return board.isWinningBoard() && (board.oneToMove() != isOne);
	}

	@Override
	public boolean isTerminal(Node node) {
		QBoard board  = (QBoard)node.getState();
		return board.gameOver() || node.getPath().size() > depthLim;
	}

	private double ourTurnUtil(Node node) {
		//return good value if the piece we're given will make us win
		QBoard board  = (QBoard)node.getState();
		boolean winNext = true;
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 4; y++) {
				QBoard nextBoard = (QBoard)board.clone();
				QMove enemyMove = new QMove(board.getNextPiece(), new Point(x,y), null);
				nextBoard.update(enemyMove);
				winNext = nextBoard.isWinningBoard();
			}
		}
		if(winNext) return 1000;
		return 0;
	}
	
	private double theirTurnUtil(Node node) {
		//return negative_inf if the piece we're giving them makes them win
		QBoard board  = (QBoard)node.getState();
		boolean dontLoseNext = true;
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 4; y++) {
				QBoard nextBoard = (QBoard)board.clone();
				QMove enemyMove = new QMove(board.getNextPiece(), new Point(x,y), null);
				nextBoard.update(enemyMove);
				dontLoseNext = dontLoseNext && !nextBoard.isWinningBoard();
			}
		}
		if(dontLoseNext) return 0;
		else return Double.NEGATIVE_INFINITY;
		//actually gives negative_inf if the piece we're giving could make them win
	}
	
	@Override
	public double utility(Node node) {
		QBoard board  = (QBoard)node.getState();
		if(isGoal(node)) return Double.POSITIVE_INFINITY;
		else 
			if(board.isWinningBoard()) return Double.NEGATIVE_INFINITY;
			else if(board.oneToMove() == isOne) return ourTurnUtil(node); //if the next turn is our turn
			else return theirTurnUtil(node);
	}

	@Override
	public void setDepthLimit(double limit) {
		depthLim = limit;
	}

	@Override
	public double getDepthLimit() {
		return depthLim;
	}

}
