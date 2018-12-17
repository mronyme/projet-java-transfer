package core;

import java.util.List;

import enums.ColorEnum;

public class Player {
	ColorEnum color;
	int numberKings;
	Board board;
	public Player(ColorEnum color,int numberKings) {
		super();
		this.color = color;
		this.numberKings = numberKings;
		this.board = new Board();
	}	
}
