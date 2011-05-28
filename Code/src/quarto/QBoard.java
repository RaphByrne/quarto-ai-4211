package quarto;

import java.awt.Point;
import java.util.ArrayList;


import agent.*;

import search.*;

public class QBoard implements Environment, Percept, State, java.lang.Cloneable, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Piece board[][];
	private ArrayList<Piece> unplayed;
	private boolean oneToMove;
	private Piece nextPiece;
	private QMove prevMove;
	private boolean firstMove = true;
	
	public QBoard() {
		board = new Piece[4][4];
		unplayed = new ArrayList<Piece>();
		unplayed.add(new Piece(true, true, true, true));
		unplayed.add(new Piece(true, true, true, false));
		unplayed.add(new Piece(true, true, false, false));
		unplayed.add(new Piece(true, false, false, false));
		unplayed.add(new Piece(true, false, false, true));
		unplayed.add(new Piece(true, false, true, true));
		unplayed.add(new Piece(true, false, true, false));
		unplayed.add(new Piece(true, true, false, true));
		
		unplayed.add(new Piece(false, false, false, false));
		unplayed.add(new Piece(false, true, true, true));
		unplayed.add(new Piece(false, false, true, true));
		unplayed.add(new Piece(false, false, false, true));
		unplayed.add(new Piece(false, true, false, false));
		unplayed.add(new Piece(false, true, false, true));
		unplayed.add(new Piece(false, true, true, false));
		unplayed.add(new Piece(false, false, true, false));
		oneToMove = true;
		nextPiece = null;
		prevMove = null;
	}
	
	private Piece[][] boardClone(Piece[][] board) {
		Piece[][] out = new Piece[board.length][board[0].length];
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				out[i][j] = board[i][j];
			}
		}
		return out;
	}
	
	public QBoard(Piece[][] board, ArrayList<Piece> unplayed, boolean oneToMove, Piece nextPiece, QMove prevMove, boolean firstMove) {
		this.board = boardClone(board);
		this.unplayed = (ArrayList<Piece>)unplayed.clone();
		this.oneToMove = oneToMove;
		if(nextPiece != null) this.nextPiece = (Piece)nextPiece.clone();
		else nextPiece = null;
		if(prevMove != null) this.prevMove = (QMove)prevMove.clone();
		else this.prevMove = null;
		this.firstMove = firstMove;
	}
	
	
	private String rowString(Piece[] row) {
		String s = "";
		for(Piece p : row) {
			s = s + p.toString() + " ";
		}
		return s;
	}
	
	
	private boolean isWinningRow(Piece[] row) {
		if(row == null || row[0] == null) return false;
		boolean black = true;
		boolean tall = true;
		boolean round = true;
		boolean solid = true;
		for(int i = 1; i < row.length; i++) {
			if(row[i] == null) return false;
			black = black && (row[0].isBlack() == row[i].isBlack());
			tall = tall && (row[0].isTall() == row[i].isTall());
			round = round && (row[0].isRound() == row[i].isRound());
			solid = solid && (row[0].isSolid() == row[i].isSolid());
		}
		//boolean result = black || tall || round || solid;
		//System.out.println("Testing:" + rowString(row) +  "- " + result);
		return black || tall || round || solid;
	} 
	
	/**
	 * Checks whether the box with top left coord x,y is winning
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isWinningBox(int x, int y) {
		//System.out.println("Testing box " + x + ", " + y);
		boolean black = true;
		boolean tall = true;
		boolean round = true;
		boolean solid = true;
		for(int i = x; i < x+2; i++) {
			for(int j = y;j < y+2; j++) {
				if(board[i][j] == null) return false;
				black = black && (board[x][y].isBlack() == board[i][j].isBlack());
				tall = tall && (board[x][y].isTall() == board[i][j].isTall());
				round = round && (board[x][y].isRound() == board[i][j].isRound());
				solid = solid && (board[x][y].isSolid() == board[i][j].isSolid());
			}
		}
		return black || tall || round || solid;
	}
	
	private boolean winningBoxes() {
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				if(isWinningBox(i, j))
					return true;
		return false;
	}
	
	public boolean isWinningBoard() {
		boolean rowWin = false;
		boolean colWin = false;
		boolean diagWin = false;
		Piece[][] cols = new Piece[4][4];
		Piece[][] diags = new Piece[2][4];
		
		for(int i = 0; i < board.length; i++) {
			if(!rowWin) rowWin = isWinningRow(board[i]);
			for(int j = 0; j < board[i].length; j++) {
				cols[i][j] = board[j][i];
			}
			diags[0][i] = board[i][i];
			diags[1][i] = board[i][3-i];
			if(!colWin) colWin = isWinningRow(cols[i]);
		}
		
		diagWin = isWinningRow(diags[0]) || isWinningRow(diags[1]);

		return rowWin || diagWin || colWin || winningBoxes();		
	}
	
	
	public boolean gameOver() {
		return unplayed.isEmpty() || isWinningBoard();
	}
	
	@Override
	public Object clone() {
		return new QBoard(board, unplayed, oneToMove, nextPiece, prevMove, firstMove);
	}
	
	@Override
	public void update(Action action) throws RuntimeException {
		if(action instanceof QMove) {
			QMove move = (QMove)action;
			firstMove = false;
			if(move.getRecieved() != null) { //if isn't the first move
				Piece placedPiece = (Piece)move.getRecieved().clone();
				Point loc = move.getLocation();
				if(board[loc.x][loc.y] == null) board[loc.x][loc.y] = placedPiece;
				if(!unplayed.remove(placedPiece))
					System.out.println("Didn't remove a piece"); 
			} 
			//piece given to other player not removed from unplayed until their next move is executed
				nextPiece = move.getGiving();
				oneToMove = !oneToMove;
		} else
			throw new RuntimeException("Move not a QMove");
	}

	@Override
	public Actions getActions() {
		Actions actions = new Actions();
		if(firstMove) {
			for(Piece p : unplayed)
				actions.add(new QMove(null, null, p));
		} else if(unplayed.size() == 1) { //if the last move (piece to put down hasn't been removed yet)
			for(int i = 0; i < board.length; i++)
				for(int j = 0; j < board[i].length; j++)
					if(board[i][j] == null)
						actions.add(new QMove(nextPiece, new Point(i,j), null));
		} else {
			for(int i = 0; i < board.length; i++)
				for(int j = 0; j < board[i].length; j++)
					if(board[i][j] == null)
						for(Piece p : unplayed)
							if(!p.equals(nextPiece))
								actions.add(new QMove(nextPiece, new Point(i,j), p));
		}
			return actions;
	}
	
	public boolean oneToMove() {
		return oneToMove;
	}

	
	
	private String unplayedString() {
		String s = "";
		for(Piece p : unplayed) {
			s = s.concat(p.toString() + " ");
		}
		return s;
	}
	
	@Override
	public String toString() {
		String s = "  --------------------\n";
		int i = 4;
		for(Piece[] p : board) {
			s = s.concat(Integer.toString(i));
			i--;
			for(Piece pp : p) {
				s = s.concat("|");
				if(pp != null)
					s = s.concat(pp.toString());
				else
					s = s.concat("    ");
				
			}
			s = s.concat("|");
			s = s.concat("\n  --------------------\n");
			
		}
		s = s.concat("    ");
		for(int j = 1; j < 5; j++) {
			s = s.concat(Integer.toString(j) + "    ");
		}
		s = s.concat("\n");
		s = s.concat("Unplayed: " + unplayedString() + "\n");
		if(nextPiece != null) s = s.concat("Next Piece to Play: " + nextPiece.toString());
		else s = s.concat("Next Piece to Play: NULL");
		s = s + "\n\n";
		return s;
	}
	
	public Piece[][] getBoard() {
		return boardClone(board);
	}
	
	public Percept getPercept() {
		return this;
	}
	
	public boolean firstMove() {
		return firstMove;
	}
	
	public Piece getNextPiece() {
		return nextPiece;
	}
	
	
	/**
	 * Takes in a string representation of attributes and returns any pieces
	 * that have those attributes.
	 * eg "BT" would return all pieces that are black and tall
	 * 
	 * Attribute names are as desribed in Piece.ToString
	 * @param descript
	 * @return
	 */
	public int numUnplayedPieces(String descript) {
		int count = 0;
		for(Piece p: unplayed) {
			String s = p.toString();
			int matchCount = 0;
			for(int j = 0; j < s.length(); j++) {
				for(int i = 0; i < descript.length(); i++) {
					if(s.charAt(j) == descript.charAt(i)) matchCount++;
				}
			}
			if(matchCount == descript.length()) count++;
		}
		return count;
	}
	
	/**
	 * Gives the number of pieces that don't have the attributes listed
	 * @param descript
	 * @return
	 */
	public int numUnplayedPiecesNOT(String descript) {
		for(int i = 0; i < descript.length(); i++) {
			char c = descript.charAt(i);
			if(Character.isLowerCase(c))
				descript.replace(c, Character.toUpperCase(c));
			else if(Character.isUpperCase(c))
				descript.replace(c, Character.toLowerCase(c));
		}
		return numUnplayedPieces(descript);	
	}
	
	public boolean isValidMove(QMove move) {
		Point loc = move.getLocation();
		return !(loc.x > 3 || loc.y > 3 || board[loc.x][loc.y] != null
				|| move.getGiving().equals(nextPiece));
	}
	
	
}
