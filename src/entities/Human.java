package entities;

import enums.ColorEnum;

public class Human extends Player{
	public Human(ColorEnum color,int numberKings) {
		super(color,numberKings);
	}
	public void startTurn()
	{
		// Appel fonction affichage du tour du joueur
	}
}
