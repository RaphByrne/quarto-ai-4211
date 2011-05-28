package players;

import players.QuartoNodeInfo;
import search.*;
import agent.Action;
import agent.Actions;
import agent.Percept;
import quarto.*;

public class AlphaBetaPlayer extends Player {

	QuartoNodeInfo nodeInfo;
	
	public AlphaBetaPlayer(boolean isOne, String name) {
		super(isOne, name);
		nodeInfo = new QuartoNodeInfo(isOne);
	}

	@Override
	public Action getAction(Percept p) {
		QBoard board = (QBoard) p;
		Node start = new Node(board);
		AlphaBeta searcher = new AlphaBeta(nodeInfo);
		if(board.firstMove()) {
			Actions actions = board.getActions();
			int choice = (int)(Math.random()*(actions.size()-1));
			return (Action)actions.get(choice);
		} else 
		 return searcher.Decision(start);
	}

}
