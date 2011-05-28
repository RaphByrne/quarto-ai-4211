package players;

import quarto.QBoard;
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

	@Override
	public double utility(Node node) {
		QBoard board  = (QBoard)node.getState();
		if(isGoal(node)) return 1;
		else 
			if(board.isWinningBoard()) return -1;
			else return 0;
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
