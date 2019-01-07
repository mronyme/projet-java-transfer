package entities;

import core.CoreGame;
import enums.ColorEnum;

public class Human extends Player{
	public Human(CoreGame game,ColorEnum color,int numberKings) {
		super(game,color,numberKings);
	}
	public void initTurn() {
		// le joueur doit placer son ch�teaux
		// La fonction a utiliser est this.getBoard().setCastle(x, y);
		// la fonction ne renvoit rien
	}
	public Card firstTurn() {
		// List<Card> cards  = game.getCardsAvailable(0); permet de r�cuperer la liste des cartes encore disponible pour le premier tour de jeux
		// tu poses la cartes sur le plateau avec la fonction this.getBoard().setCard(face1X,face1Y, face2X, face2Y, card);
		// avec face1X la coordonn�es x de la face 1 de la carte
		// avec face1Y la coordonn�es y de la face 1 de la carte
		// avec face2X la coordonn�es x de la face 2 de la carte
		// avec face2Y la coordonn�es y de la face 2 de la carte
		// avec card la carte � pos�
		// List<Card> cards  = game.getCardsAvailable(1); permet de r�cuperer la liste des cartes du prochain tour de jeux ( deuxi�me colone)
		// la fonction renvoie la carte qu'il jouera au prochain tour ( la carte de la deuxi�me colone o� il posera son roi)
		return null;
	}
	public void lastTurn(int nbRound,Card lastCard) {
		// tu poses la derni�re cartes sur le plateau avec la fonction this.getBoard().setCard(face1X,face1Y, face2X, face2Y, card);
		// avec face1X la coordonn�es x de la face 1 de la carte
		// avec face1Y la coordonn�es y de la face 1 de la carte
		// avec face2X la coordonn�es x de la face 2 de la carte
		// avec face2Y la coordonn�es y de la face 2 de la carte
		// avec card la carte � pos�
		// la fonction ne renvoit rien
	}
	public Card startTurn(int nbRound,Card card)
	{
		// List<Card> cards  = game.getCardsAvailable(0); permet de r�cuperer la liste des cartes encore disponible pour le premier tour de jeux
		// tu poses la cartes sur le plateau avec la fonction this.getBoard().setCard(face1X,face1Y, face2X, face2Y, card);
		// avec face1X la coordonn�es x de la face 1 de la carte
		// avec face1Y la coordonn�es y de la face 1 de la carte
		// avec face2X la coordonn�es x de la face 2 de la carte
		// avec face2Y la coordonn�es y de la face 2 de la carte
		// avec card la carte � pos�
		// List<Card> cards  = game.getCardsAvailable(1); permet de r�cuperer la liste des cartes du prochain tour de jeux ( deuxi�me colone)
		// la fonction renvoie la carte qu'il jouera au prochain tour ( la carte de la deuxi�me colone o� il posera son roi)
		return null;
	}
}
