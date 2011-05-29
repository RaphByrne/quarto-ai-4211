package quarto;

import agent.Agent;

public abstract class Player implements Agent {

	private String name;
	private boolean isOne;
	
	public Player(boolean isOne, String name) {
		this.name = name;
		this.isOne = isOne;
	}
	
	public String getName() {
		return name;
	}
	
	public Object clone() {
		return this;
	}
	
}
