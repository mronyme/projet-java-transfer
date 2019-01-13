package entities;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import core.CoreGame;
import enums.ColorEnum;

public abstract class Player {
	ColorEnum color;
	int numberKings;
	Board board;
	CoreGame game;
	HashMap<String,Integer> finalScore;
	public Player(CoreGame game,ColorEnum color,int numberKings) {
		super();
		this.game = game;
		this.color = color;
		this.numberKings = numberKings;
		this.board = new Board(game,this);
		this.finalScore = new HashMap<String,Integer>();
	}
	public void initTurn() {
		// le joueur doit placer son châteaux
		// la fonction ne renvoit rien
	}
	public Card firstTurn() {
		
		return null;
	}
	public void lastTurn(int nbRound,Card card) {}
	public Card startTurn(int nbRound,Card card) {
		return null;
	}
	
	public Board getBoard() {
		return this.board;
	}
	public HashMap<String,Integer> getFinalScore() {
		return this.finalScore;
	}
	public void setFinalScore(HashMap<String,Integer> finalScore) {
		this.finalScore = finalScore;
	}
	public ColorEnum getColor() {
		return this.color;
	}
	public static Player findPlayerByColor(List<Player> players, ColorEnum color){
	    Optional<Player> playerOptional = players.stream().filter(c -> c.getColor().equals(color)).findAny();
	    return playerOptional.isPresent() ? playerOptional.get() : null;
	    
	}
	public void casualTurn() {
		// TODO Auto-generated method stub
		
	}
}
