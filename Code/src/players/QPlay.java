package players;

import quarto.*;

public class QPlay {

	private static void testGame(Player p1, Player p2, boolean verbose) {
		Quarto game = new Quarto(p1, p2);
		if(verbose) System.out.println("Initial Board: \n" + game.printBoard());
		while(!game.gameOver()) {
			if(verbose) System.out.println(game.getNextPlayer().getName());
			game.step();
			if(verbose) System.out.print(game.printBoard());
		}
		if(game.oneHasWon()) System.out.println(p1.getName() + " has Won");
		else if(game.twoHasWon()) System.out.println(p2.getName() + " has Won");
		else System.out.println("Draw - Everybody loses");
	}
	
	private static void testGame(Player p1, Player p2, int numGames, boolean verbose) {
		int p1Wins = 0;
		int p2Wins = 0;
		int draws = 0;
		for(int i = 0; i < numGames; i++) {
			p1 = (Player)p1.clone();
			p2 = (Player)p2.clone();
			Quarto game = new Quarto(p1, p2);
			if(verbose) System.out.println("Initial Board: \n" + game.printBoard());
			while(!game.gameOver()) {
				if(verbose) System.out.println(game.getNextPlayer().getName());
				game.step();
				if(verbose) System.out.print(game.printBoard());
			}
			if(game.oneHasWon()) p1Wins++;
			else if(game.twoHasWon()) p2Wins++;
			else draws++;
		}
		System.out.print(p1.getName() + ": " + p1Wins + "\n" + p2.getName() + ": " + p2Wins
				+ "\n" + "Draws: " + draws + "\n");
	}
	
	public static void main(String[] args) {
		
		Player p1 = new RandomRob(false, "Rob1");
		Player missy = new NoMistakeOnePly(true, "Missy");
		Player alpha = new AlphaBetaPlayer(false, "Alpha");
		Player steve = new SimpleStrat(false, "Steve");
		Player human = new HumanPlayer(false, "John Smith");
		
		//testGame(missy, alpha, true);
		testGame(missy, alpha, 100, false);
		
	}
	
}
