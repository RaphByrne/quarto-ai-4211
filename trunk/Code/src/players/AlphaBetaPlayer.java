package players;


import search.*;
import agent.Action;
import agent.Actions;
import agent.Percept;
import quarto.*;

public class AlphaBetaPlayer extends Player {

	NodeInfo nodeInfo;
	Player noMistake;
	int moveCount;
	String name;
	boolean isOne;
	
	public AlphaBetaPlayer(boolean isOne, String name) {
		super(isOne, name);
		this.isOne = isOne;
		nodeInfo = new PerfectPlayNodeInfo(isOne, 8);
		noMistake = new NoMistakeOnePly(isOne);
		if(isOne) moveCount = -1;
		else moveCount = 0;
		this.name = name;
	}

	@Override
	public Object clone() {
		return new AlphaBetaPlayer(isOne, name);
	}
	
	@Override
	public Action getAction(Percept p) {
		moveCount++;
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
		} else if(moveCount > 4){ //if there have been 8 moves we can search
			Node start = new Node(board);
			//long begin = System.currentTimeMillis();
			AlphaBeta searcher = new AlphaBeta(nodeInfo);
			Action alpha = searcher.decision(start);
			//long end = System.currentTimeMillis();
			//long total = end - begin;
			//System.out.println("Alphabeta time: " + total);
			return alpha;
		} else {
			System.out.println("Alpha used noMistake");
			return next;
		}
			
	}

}
