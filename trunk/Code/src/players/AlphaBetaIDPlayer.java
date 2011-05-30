package players;

import search.*;
import agent.Action;
import agent.Actions;
import agent.Percept;
import quarto.*;

public class AlphaBetaIDPlayer extends Player {

	NodeInfo nodeInfo;
	Player noMistake;
	String name;
	boolean isOne;
	
	public AlphaBetaIDPlayer(boolean isOne, String name) {
		super(isOne, name);
		this.isOne = isOne;
		nodeInfo = new PerfectPlayNodeInfo(isOne, 8);
		noMistake = new NoMistakeOnePly(isOne);
		this.name = name;
	}

	@Override
	public Object clone() {
		return new AlphaBetaIDPlayer(isOne, name);
	}
	
	@Override
	public Action getAction(Percept p) {
		long startTime = System.currentTimeMillis();
		QBoard q = (QBoard) p;
		QBoard board = (QBoard)q.clone();
		
		Action next = noMistake.getAction(p);
		QBoard clone = (QBoard)q.clone();
		clone.update(next);
		
		if(clone.isWinningBoard()) return next;
		if(board.firstMove()) {
			Actions actions = board.getActions();
			int choice = (int)(Math.random()*(actions.size()-1));
			return (Action)actions.get(choice);
		} else { 
			Node start = new Node(board);
			long here = System.currentTimeMillis();
			AlphaBetaID searcher = new AlphaBetaID(nodeInfo, start, 5000 - (here - startTime));
			searcher.run();
			if(searcher.best != null) return searcher.best;
			else return next;
			
		}
			
	}

}
