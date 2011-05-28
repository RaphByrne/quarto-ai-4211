package players;

import agent.Action;
import agent.Percept;
import quarto.Piece;
import quarto.Player;
import quarto.QBoard;
import quarto.QMove;

import java.awt.Point;
import java.io.*;

public class HumanPlayer extends Player {

	boolean isOne;
	
	public HumanPlayer(boolean isOne, String name) {
		super(isOne, name);
		this.isOne = isOne;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action getAction(Percept p) {
		String prompt = "Human Player ";
		if(isOne) prompt = prompt.concat("One: ");
		else prompt = prompt.concat("Two: ");
		prompt = prompt + "Enter your move (placement then next piece)";
		System.out.print(prompt);
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String sMove = readline(br);
		
		QBoard board = (QBoard)p;
		int x = 5-Character.getNumericValue(sMove.charAt(2))-1;
		int y = Character.getNumericValue(sMove.charAt(0))-1;
		Point loc = new Point(x, y);
		Piece giving = new Piece(sMove.substring(4));
		QMove ourMove = new QMove(board.getNextPiece(), sMove);
		if(board.isValidMove(ourMove)) return ourMove;
		else {
			System.out.println("Not a valid move");
			return getAction(p);
		}
	}
	
	public String readline(BufferedReader br) {
		String sMove = "";
		try {
			sMove = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return sMove;
	}

}
