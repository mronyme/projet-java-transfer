package core;

import java.util.ArrayList;
import java.util.List;

import enums.ColorEnum;

public class Player {
	ColorEnum color;
	int numberKings;
	Board board;
	List<Integer> finalScore;
	public Player(ColorEnum color,int numberKings) {
		super();
		this.color = color;
		this.numberKings = numberKings;
		this.board = new Board();
		this.finalScore = new ArrayList<Integer>();
	}
	public List<Integer> getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(int totalScore,int largestDomain,int nbCouronnes) {
		this.finalScore.add(totalScore);
		this.finalScore.add(largestDomain);
		this.finalScore.add(nbCouronnes);
	}	
}
