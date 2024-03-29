package players;

import quarto.*;

public class QPlay {

	/**
	 * Runs a single game between two players
	 * @param p1 the first player
	 * @param p2 the second player
	 * @param verbose if true will print out text based descriptions of each move
	 * to std out
	 */
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
	
	/**
	 * Runs a volume test of games, printing win loss stats to stdout upon completion
	 * @param p1 the first player
	 * @param p2 the second player
	 * @param numGames the number of tests to run
	 * @param verbose if true will print out text based descriptions of each move
	 * to std out
	 */
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
		
		Player alpha1 = new AlphaBetaPlayer(true, "Alpha");
		Player alpha2 = new AlphaBetaPlayer(false, "Alpha");
		Player human = new HumanPlayer(true, "John Smith");
		Player alphaID1 = new AlphaBetaIDPlayer(true, "AlphaID");
		Player alphaID2 = new AlphaBetaIDPlayer(false, "AlphaID");
		Player missy1 = new NoMistakeOnePly(true, "Missy");
		Player missy2 = new NoMistakeOnePly(false, "Missy");
		Player symmetry1 = new SymmetryPlayer(true, "Simmo");
		Player symmetry2 = new SymmetryPlayer(false, "Simmo");
		//testGame(missy, alpha, true);
		//testGame(alphaID1, missy2, 10, false);
		testGame(human, alpha2, true);
		
	}
	
}
