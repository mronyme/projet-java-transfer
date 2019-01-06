package entities;

import core.CoreGame;
import enums.ColorEnum;

public class Human extends Player{
	public Human(CoreGame game,ColorEnum color,int numberKings) {
		super(game,color,numberKings);
	}
	public void initTurn()
	{
		// Appel fonction demandant de placer le château sur le terrain
	}
	public Card firstTurn() {
		// Appel fonction pour premier tour
		return null;
	}
	public void lastTurn(int nbRound,Card card) {
		// Appel fonction pour le dernier tour
	}
	public Card startTurn(int nbRound,Card card)
	{
		if(card == null)
		{
			
		}
		else
		{
			
		}
		return null;
		// Appel fonction affichage du tour du joueur
	}
}
