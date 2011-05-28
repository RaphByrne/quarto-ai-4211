package quarto;

public class Piece implements java.lang.Cloneable {

	private boolean black;
	private boolean round;
	private boolean solid;
	private boolean tall;
	
	

	/**
	 * @param black - is piece black
	 * @param round - is piece round
	 * @param solid - is piece solid
	 * @param tall - is piece tall
	 */
	public Piece(boolean black, boolean round, boolean solid, boolean tall) {
		this.black = black;
		this.round = round;
		this.solid = solid;
		this.tall = tall;
	}
	
	/**
	 * Given a string description, creates the appropriate piece
	 * @param s follows the pattern defined in toString()
	 */
	public Piece(String s) {
		
		if(s.contains("B")) black = true;
		else if(s.contains("b")) black = false;
		
		if(s.contains("T")) tall = true;
		else if(s.contains("t")) tall = false;
		
		if(s.contains("R")) round = true;
		else if(s.contains("r")) round = false;
		
		if(s.contains("S")) solid = true;
		else if(s.contains("s")) solid = false;
	}
	
	
	@Override
	public boolean equals(Object piece) {
		Piece p2 = (Piece)piece;
		return (this.isBlack() == p2.isBlack()) 
				&& (this.isRound() == p2.isRound()) 
				&& (this.isSolid() == p2.isSolid())
				&& (this.isTall() == p2.isTall());
	}
	
	/**
	 * Returns true if this piece and the argument piece share at least one attribute
	 * @param piece
	 * @return
	 */
	public boolean shareAttribute(Piece p2) {
		return (this.isBlack() == p2.isBlack()) 
			|| (this.isRound() == p2.isRound()) 
			|| (this.isSolid() == p2.isSolid())
			|| (this.isTall() == p2.isTall());
	}
	
	@Override
	public Object clone() {
		return new Piece(black, round, solid, tall);
	}
	
	public boolean isBlack() {
		return black;
	}

	public boolean isRound() {
		return round;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isTall() {
		return tall;
	}
	
	@Override
	public String toString() {
		String s = "";
		if(black) s = s.concat("B");
		else s = s.concat("b");
		if(round) s = s.concat("R");
		else s = s.concat("r");
		if(solid) s = s.concat("S");
		else s = s.concat("s");
		if(tall) s = s.concat("T");
		else s = s.concat("t");
		return s;
	}
	
	public boolean hasAttribute(String attr) {
		return toString().contains(attr);
	}
	
}
