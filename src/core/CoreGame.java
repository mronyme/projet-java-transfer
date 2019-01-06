package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import entities.*;
import enums.*;
import views.*;

import enums.ColorEnum;
import views.Window;

public class CoreGame {
    static final int twoPlayerCardCount = 24; // Nb carte de d�part pour deux joueurs
    static final int threePlayerCardCount = 36; // Nb carte de d�part pour trois joueurs
    static final int fourPlayerCardCount = 48; // Nb carte de d�part pour quatre joueurs
    
    List<Player> players;       //Liste des joueurs
    List<ColorEnum> colorsAvailable;     // Liste des couleurs disponibles lors de la r�partition des couleurs pour chaque joueur
    Map<ColorEnum, Integer> kingsList;     // Liste des rois restant pour chaque couleur � piocher lors du premier tour
    int numberTotalKings; // Nombre de rois totaux pr�sent dans la partie
    CardManagement cardManagement;
    public CoreGame() {
        this.players = new ArrayList<Player>();
        this.colorsAvailable = new ArrayList<>(Arrays.asList(ColorEnum.pink,ColorEnum.yellow,ColorEnum.green,ColorEnum.blue));
        this.cardManagement = new CardManagement(this);
        this.kingsList = new HashMap<ColorEnum, Integer>();
        cardManagement.initCard();
        initGame(4);
    }
    // Cette fonction g�re le commencement de la partie
    // Valeur en entr�e : nombre de joueurs
    public void initGame(int numberPlayers) {
        if (numberPlayers == 2) {
            this.numberTotalKings = 2 * numberPlayers;
            cardManagement.setNumberTotalKings(this.numberTotalKings);
            initPlayers(numberPlayers, 2);
        } else {
            this.numberTotalKings = numberPlayers;
            cardManagement.setNumberTotalKings(this.numberTotalKings);
            initPlayers(numberPlayers, 1);
        }
        cardManagement.discardCards(numberPlayers);
        manageRound(0);
    }
    // Fonction g�rant les tours de jeux jusqu'� la fin
    public void manageRound(int nbRound) {
    	if(cardManagement.getCardsListSize() != 0)
    	{
    		// D�clenche phase pr�paration 3.1
    		if(nbRound == 0)
    		{
    			initRound();
    		}
    		else
    		{
    			// D�clenche phase premier tour 3.2
	    		if(nbRound == 1)
	    		{
	    			firstRound();
	    		}
	    		// D�clenche tour de jeux habituels 3.3
	    		else
	    		{
	    			casualRound(nbRound);
	    		}
	    		// Retire la premi�re ligne de domino ( qui a �t� utilis� pendant le tour)
	    		cardManagement.clearCardColumn(0);
    		}
    		// D�clenche le tour suivant
    		manageRound(nbRound+1);
    	}
    	// Si il reste une ligne de domino � pos� , on enclenche le dernier tour
    	else if(cardManagement.getCardsColumnSize()  != 0)
    	{
    		lastRound(nbRound);
    		cardManagement.clearCardColumn(0);
    		// fonction g�rant la fin du jeux
    		endGame();
    	}
    }
    public void initRound() {
    	for(Player player : players) {
    		player.initTurn();
    	}
    }
    public void firstRound() {
    	List<ColorEnum> order = drawKings();
    	cardManagement.drawFirstRound();
    	for(ColorEnum color : order)
    	{
    		Player player = Player.findPlayerByColor(players, color);
    		Card nextCard = player.firstTurn();
    		cardManagement.pickCard(player,1,nextCard);
    	}
    }
    public void lastRound(int nbRound) {
    	for(HashMap<String,Object> map : cardManagement.getCardsColumn().get(0))
    	{
    		Player player = Player.findPlayerByColor(players, (ColorEnum)map.get("color"));
    		player.lastTurn(nbRound,(Card)map.get("card"));
    	}
    }
    public void casualRound(int nbRound) {
    	cardManagement.drawCasualRound();
    	//System.out.println(cardsColumn);
    	for(HashMap<String,Object> map : cardManagement.getCardsColumn().get(0))
    	{
    		Player player = Player.findPlayerByColor(players, (ColorEnum)map.get("color"));
    		Card nextCard = player.startTurn(nbRound,(Card)map.get("card"));
    		cardManagement.pickCard(player,1,nextCard);
    	}
    }
    public void endGame()
    {
    	System.out.println("Fin du jeux");
		List<Player> playersRanked = ScoreManagement.getLeaderBoard(players);
    	for(Player player : players)
    	{
    		System.out.println(player+" "+player.getFinalScore());
    		Board.printMatrix(player.getBoard().getFaceEnum());    		
    	}
		System.out.println(playersRanked.get(0)+" a gagn� !");
    	// fin de partie
    } 
    public List<Card> getCardsAvailable(int columnId) {
    	return cardManagement.getCardsAvailable(columnId);
    }
    public void pickCard(Player player,int columnId,Card card) {
    	cardManagement.pickCard(player, columnId, card);
    }
    // Cette fonction g�re l'ordre de jeu du premier tour en respectant l'ordre de pioche al�atoire des n rois
    // Valeur Retourn�e : Liste ordonn�es de n couleur correspondant au rois des joueurs respectifs -> Ainsi le premier �lement de la liste est le premier roi pioch� et donc le joueur de cette couleur commencera etc.
    // Cette fonction g�re l'ordre de jeu du premier tour en respectant l'ordre de pioche al�atoire des n rois
    // Valeur Retourn�e : Liste ordonn�es de n couleur correspondant au rois des joueurs respectifs -> Ainsi le premier �lement de la liste est le premier roi pioch� et donc le joueur de cette couleur commencera etc.
    // n -> Nombres de rois totaux dans la partie
    public List<ColorEnum> drawKings()
    {
		Random rand = new Random();
		List<ColorEnum> kingsPicked = new ArrayList<ColorEnum>();
    	for(int i = 0;i < numberTotalKings;i++)
    	{
    		
    		ColorEnum[] kingsRemaining = (ColorEnum[])(kingsList.keySet().toArray(new ColorEnum[kingsList.size()]));
    		ColorEnum kingColor = kingsRemaining[rand.nextInt(kingsRemaining.length)];
    		kingsList.put(kingColor,kingsList.get(kingColor) -1);
    		if(kingsList.get(kingColor) == 0)
    		{
    			kingsList.remove(kingColor);
    		}
    		kingsPicked.add(kingColor);
    	}
    	return kingsPicked;
    }
    
    // Cette fonction instantie les diff�rents joueurs
    // Cette fonction instantie les diff�rents joueurs
    public void initPlayers(int numberPlayers, int numberKings) {
		for(int i = 0;i < numberPlayers;i++)
		{
			Random rand = new Random();
			ColorEnum color = colorsAvailable.get(rand.nextInt(colorsAvailable.size()));
			Player player = new Ia(this,color,numberKings);
			this.kingsList.put(color, numberKings);
			colorsAvailable.remove(color);
			players.add(player);
		}
    }
}

