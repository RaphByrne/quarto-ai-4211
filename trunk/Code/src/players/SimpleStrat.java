package players;

import java.awt.Point;
import java.util.Iterator;

import agent.Action;
import agent.Actions;
import agent.Percept;
import quarto.Player;
import quarto.QBoard;
import quarto.QMove;

public class SimpleStrat extends Player {

	String attr;
	
	public SimpleStrat(boolean isOne, String name) {
		super(isOne, name);
		String[] attributes = new String[8];
		attributes[0] = "b";
		attributes[1] = "B";
		attributes[2] = "t";
		attributes[3] = "T";
		attributes[4] = "r";
		attributes[5] = "R";
		attributes[6] = "s";
		attributes[7] = "S";
		int choice = (int)(Math.random()*(attributes.length-1));
		attr = attributes[choice];
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action getAction(Percept p) {
		QBoard board = (QBoard) p;
		Actions actions = board.getActions();
		Actions nonLosing = new Actions();
		for(Iterator<Action> i = actions.iterator(); i.hasNext();) {
			QMove move = (QMove)i.next();
			QBoard nextBoard = (QBoard)board.clone();
			nextBoard.update(move);
			if(nextBoard.isWinningBoard()) return move;
			boolean dontLoseNext = true;
			for(int x = 0; x < 4; x++) {
				for(int y = 0; y < 4; y++) {
					QBoard nextBoard2 = (QBoard)nextBoard.clone();
					QMove enemyMove = new QMove(move.getGiving(), new Point(x,y), null);
					nextBoard2.update(enemyMove);
					dontLoseNext = dontLoseNext && !nextBoard2.isWinningBoard();
				}
			}
			if(dontLoseNext) nonLosing.add(move);
		}
		
		if(nonLosing.size() > 0) {
			if((8-board.numUnplayedPieces(attr)) > 5) {
				//doesn't check for adjacency
				for(Iterator<Action> i = nonLosing.iterator(); i.hasNext();) {
					QMove move = (QMove)i.next();
					if(move.getGiving().hasAttribute(attr)) return move;
				}
			} else if(board.numUnplayedPiecesNOT(attr)%2 == 0) {
				for(Iterator<Action> i = nonLosing.iterator(); i.hasNext();) {
					QMove move = (QMove)i.next();
					if(move.getGiving().hasAttribute(attr)) return move;
				}
			} else {
				for(Iterator<Action> i = nonLosing.iterator(); i.hasNext();) {
					QMove move = (QMove)i.next();
					if(move.getGiving()!= null && !move.getGiving().hasAttribute(attr)) return move;
				}
			}
		}
		//if we didn't find a board that would not make us lose then pick a random move
		int choice = (int)(Math.random()*(actions.size()-1));
		return (Action)actions.get(choice);
	}

}
