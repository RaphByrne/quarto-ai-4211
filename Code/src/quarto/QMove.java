package quarto;

import java.awt.Point;

import agent.Action;
/**
 * A move consists of placing the piece you were given and selecting the next piece for your opponent
 * except in the special case of the first move where you only select the next piece.
 * (are given a null piece)
 * @author Raph
 *
 */
public class QMove implements Action {

	private Piece giving;
	private Piece recieved;
	private Point location; //location that we put the piece we were given
	
	/**
	 * 
	 * @param recieved the piece recieved by the current player to place (null if first move)
	 * @param location the location chosen for the piece to go
	 * @param giving the piece chosen to give to the other player
	 */
	public QMove(Piece recieved, Point location, Piece giving) {
		if(recieved == null) this.recieved = null;
		else this.recieved = (Piece)recieved.clone();
		this.location = location;
		if(giving == null) this.giving = null;
		else this.giving = (Piece)giving.clone();
	}
	
	/**
	 * Given a string representation of a move, parses it and intialises a move
	 * @param s the string representation in the form: x,y piece
	 */
	public QMove(Piece recieved, String s) {
		if(recieved == null) this.recieved = null;
		else this.recieved = (Piece)recieved.clone();
		int x = 5-Character.getNumericValue(s.charAt(2))-1;
		int y = Character.getNumericValue(s.charAt(0))-1;
		this.location = new Point(x, y);
		this.giving = new Piece(s.substring(4));
	}
	
	@Override
	public Object clone() {
		return new QMove(recieved, location, giving);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof QMove) {
			QMove move = (QMove)o;
			return recieved.equals(move.recieved) && location.equals(move.location) && giving.equals(move.giving);
		} else
			return false;
	}
	
	@Override
	public double getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Piece getGiving() {
		return giving;
	}

	public Piece getRecieved() {
		return recieved;
	}

	public Point getLocation() {
		return location;
	}
	
	@Override
	public String toString() {
		if(location == null)
			return "QMove: " + "null" +  ", " + "null" + ", " + giving.toString();
		else if(recieved != null && giving == null) {
			return "QMove: " + recieved.toString() +  ", " + location.toString() + ", " + "null";
		} else if(recieved == null && giving != null)
			return "QMove: " + "null" +  ", " + location.toString() + ", " + giving.toString();
		else if(recieved == null && giving == null)
			return "QMove: " + "null" +  ", " + location.toString() + ", " + "null";
		else
			return "QMove: " + recieved.toString() +  ", " + location.toString() + ", " + giving.toString();
	}
	

}
