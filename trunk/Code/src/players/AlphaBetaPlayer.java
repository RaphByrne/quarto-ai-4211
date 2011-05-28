package players;

import players.QuartoNodeInfo;
import search.*;
import agent.Action;
import agent.Actions;
import agent.Percept;
import quarto.*;

public class AlphaBetaPlayer extends Player {

	NodeInfo nodeInfo;
	Player noMistake;
	int moveCount;
	
	public AlphaBetaPlayer(boolean isOne, String name) {
		super(isOne, name);
		nodeInfo = new PerfectPlayNodeInfo(isOne, 8);
		noMistake = new NoMistakeOnePly(isOne);
		moveCount = 0;

	}

	@Override
	public Action getAction(Percept p) {
		moveCount++;
		QBoard q = (QBoard) p;
		QBoard board = (QBoard)q.clone();
		if(board.firstMove()) {
			Actions actions = board.getActions();
			int choice = (int)(Math.random()*(actions.size()-1));
			return (Action)actions.get(choice);
		} else if(moveCount > 4){ //if there have been 10 moves
			Node start = new Node(board);
			AlphaBeta searcher = new AlphaBeta(nodeInfo);
			Action alpha = searcher.Decision(start);
			start.update(alpha);
			if(nodeInfo.utility(start) == 0)
				return noMistake.getAction(p);
			else return alpha;
		} else
			return noMistake.getAction(p);
	}

}
