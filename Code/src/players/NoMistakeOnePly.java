package players;

import java.awt.Point;
import java.util.Iterator;

import agent.*;

import agent.Percept;
import quarto.*;

public class NoMistakeOnePly extends Player {

	boolean isOne;
	
	public NoMistakeOnePly(boolean isOne, String name) {
		super(isOne, name);
		this.isOne = isOne;
		// TODO Auto-generated constructor stub
	}
	
	public NoMistakeOnePly(boolean isOne) {
		super(isOne, "Missy");
	}

	@Override
	public Object clone() {
		return new NoMistakeOnePly(isOne);
	}
	
	@Override
	public Action getAction(Percept p) {
		QBoard board = (QBoard) p;
		Actions actions = board.getActions();
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
			if(dontLoseNext) return move;
			
		}
		//if we didn't find a board that would not make us lose then pick a random move
		int choice = (int)(Math.random()*(actions.size()-1));
		return (Action)actions.get(choice);
	}

}