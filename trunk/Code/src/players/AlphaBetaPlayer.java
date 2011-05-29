package players;

import java.util.Date;

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
	String name;
	boolean isOne;
	Date theTime;
	
	public AlphaBetaPlayer(boolean isOne, String name) {
		super(isOne, name);
		this.isOne = isOne;
		nodeInfo = new PerfectPlayNodeInfo(isOne, 8);
		noMistake = new NoMistakeOnePly(isOne);
		if(isOne) moveCount = -1;
		else moveCount = 0;
		this.name = name;
		theTime = new Date();
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
		//System.out.println("Alpha got board: \n" + board.toString());
		Action next = noMistake.getAction(p);
		QBoard clone = (QBoard)q.clone();
		clone.update(next);
		if(clone.isWinningBoard()) return next;
		if(board.firstMove()) {
			Actions actions = board.getActions();
			int choice = (int)(Math.random()*(actions.size()-1));
			return (Action)actions.get(choice);
		} else if(moveCount > 4){ //if there have been 10 moves
			Node start = new Node(board);
			long begin = System.currentTimeMillis();
			AlphaBetaID searcher = new AlphaBetaID(nodeInfo);
			Action alpha = searcher.IDSearch(start, next);
			//AlphaBeta searcher = new AlphaBeta(nodeInfo);
			//Action alpha = searcher.Decision(start);
			long end = System.currentTimeMillis();
			long total = end - begin;
			System.out.println("Alphabeta time: " + total);
			return alpha;
		} else {
			System.out.println("Alpha used noMistake");
			return next;
		}
			
	}

}
