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
		QBoard board = (QBoard)p;
		
		String prompt = "Human Player ";
		if(isOne) prompt = prompt.concat("One: ");
		else prompt = prompt.concat("Two: ");
		if(board.firstMove())
			prompt = prompt + "Enter your move (the piece you are giving)";
		else
			prompt = prompt + "Enter your move (placement then next piece)";
		System.out.print(prompt);
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String sMove = readline(br);
		
		Point loc;
		Piece giving;
		Piece received;
		if(board.firstMove()) {
			loc = null;
			giving = new Piece(sMove);
			received = null;
		} else {
			int x = 5-Character.getNumericValue(sMove.charAt(2))-1;
			int y = Character.getNumericValue(sMove.charAt(0))-1;
			loc = new Point(x, y);
			String pieceString = sMove.substring(4);
			if(!pieceString.equals("")) //if we're not giving a "null" piece i.e. on the last move
				giving = new Piece(pieceString);
			else
				giving = null;
			received = board.getNextPiece();
		}
		QMove ourMove = new QMove(received, loc, giving);
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
