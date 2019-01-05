package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import core.CoreGame;
import enums.ColorEnum;

public abstract class Player {
	ColorEnum color;
	int numberKings;
	Board board;
	CoreGame game;
	List<Integer> finalScore;
	public Player(CoreGame game,ColorEnum color,int numberKings) {
		super();
		this.game = game;
		this.color = color;
		this.numberKings = numberKings;
		this.board = new Board(game,this);
		this.finalScore = new ArrayList<Integer>();
	}
	public void initTurn() {}
	public Card startTurn(int nbRound,Card card) {
		return null;
	}
	
	public Board getBoard() {
		return this.board;
	}
	public List<Integer> getFinalScore() {
		return this.finalScore;
	}
	public void setFinalScore(int totalScore,int largestDomain,int nbCouronnes) {
		this.finalScore.add(totalScore);
		this.finalScore.add(largestDomain);
		this.finalScore.add(nbCouronnes);
	}
	public ColorEnum getColor() {
		return this.color;
	}
	public static Player findPlayerByColor(List<Player> players, ColorEnum color){
	    Optional<Player> playerOptional = players.stream().filter(c -> c.getColor().equals(color)).findAny();
	    return playerOptional.isPresent() ? playerOptional.get() : null;
	    
	}
}
