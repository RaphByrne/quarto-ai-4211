package players;

import java.awt.Point;

import quarto.QBoard;
import quarto.QMove;
import search.Node;
import search.NodeInfo;

public class PerfectPlayNodeInfo implements NodeInfo {

	boolean isOne;
	double depthLim;
	
	public PerfectPlayNodeInfo(boolean isOne, double depthLim) {
		this.isOne = isOne;
		this.depthLim = depthLim;
	}

	@Override
	public boolean isGoal(Node node) {
		QBoard board  = (QBoard)node.getState();
		return board.isWinningBoard() && (board.oneToMove() != isOne);
	}

	@Override
	public boolean isTerminal(Node node) {
		QBoard board  = (QBoard)node.getState();
		return board.gameOver() || node.getPath().size() >= depthLim;
	}

	private double util2(QBoard board) {
		if(board.oneToMove() != isOne) {
			for(int x = 0; x < 4; x++) {
				for(int y = 0; y < 4; y++) {
					QBoard bClone = (QBoard)board.clone();
					QMove enemyMove = new QMove(board.getNextPiece(), new Point(x,y), null);
					bClone.update(enemyMove);
					if(bClone.isWinningBoard()) return -1;
					else continue;
				}
			}
			return 0;
		} else {
			for(int x = 0; x < 4; x++) {
				for(int y = 0; y < 4; y++) {
					QBoard bClone = (QBoard)board.clone();
					QMove myMove = new QMove(board.getNextPiece(), new Point(x,y), null);
					bClone.update(myMove);
					if(bClone.isWinningBoard()) return 1;
					else continue;
				}
			}
			return 0;
		}
	}
	
	@Override
	public double utility(Node node) {
		QBoard board  = (QBoard)node.getState();
		if(isGoal(node)) return 1;
		else 
			if(board.isWinningBoard()) return -1;
			else return util2(board);
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
