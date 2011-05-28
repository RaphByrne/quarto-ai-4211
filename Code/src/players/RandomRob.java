package players;

import agent.*;

import agent.Percept;
import quarto.*;

public class RandomRob extends Player {

	public RandomRob(boolean isOne, String name) {
		super(isOne, name);
		// TODO Auto-generated constructor stub
	}
	
	public RandomRob(boolean isOne) {
		super(isOne, "RandomRob");
	}

	@Override
	public Action getAction(Percept p) {
		QBoard board = (QBoard) p;
		Actions actions = board.getActions();
		int choice = (int)(Math.random()*(actions.size()-1));
		return (Action)actions.get(choice);
	}

}
