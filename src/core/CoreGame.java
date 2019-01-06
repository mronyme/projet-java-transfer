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
    List<Card> cardsList;     // Liste des Cartes totales pr�sentent dans le jeux � tout instant
    List<ColorEnum> colorsAvailable;     // Liste des couleurs disponibles lors de la r�partition des couleurs pour chaque joueur
    Map<ColorEnum, Integer> kingsList = new HashMap<ColorEnum, Integer>();     // Liste des rois restant pour chaque couleur � piocher lors du premier tour
    int numberTotalKings; // Nombre de rois totaux pr�sent dans la partie
    List<List<HashMap<String,Object>>> cardsColumn = new ArrayList<List<HashMap<String,Object>>>();	
    
    public CoreGame() {
        this.players = new ArrayList<Player>();
        this.cardsList = new ArrayList<>();
        this.colorsAvailable = new ArrayList<>(Arrays.asList(ColorEnum.pink,ColorEnum.yellow,ColorEnum.green,ColorEnum.blue));
        initCard();
        initGame(2);
    }
    // -------> Utile Pour vous <-------
    // Cette fonction g�re le commencement de la partie
    // Valeur en entr�e : nombre de joueurs
    public void initGame(int numberPlayers) {
        if (numberPlayers == 2) {
            this.numberTotalKings = 2 * numberPlayers;
            initPlayers(numberPlayers, 2);
        } else {
            this.numberTotalKings = numberPlayers;
            initPlayers(numberPlayers, 1);
        }
        discardCards();
        manageRound(0);
        // D�marrer le jeux ( lancer l'affichage des terrains)
    }
    public void manageRound(int nbRound) {
    	//System.out.println("Carte restante :"+cardsList.size());
    	//System.out.println("Carte restante :"+cardsList);
    	if(cardsList.size() != 0)
    	{
    		if(nbRound == 0)
    		{
    			initRound();
    			//manageRound(1);
    		}
    		else
    		{
	    		if(nbRound == 1)
	    		{
	    			firstRound();
	    		}
	    		else
	    		{
	    			casualRound(nbRound);
	    		}
	    		clearCardColumn(0);
    		}
    		manageRound(nbRound+1);
    	}
    	else if(cardsColumn.size() != 0)
    	{
    		lastRound(nbRound);
    		clearCardColumn(0);
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
    	drawFirstRound();
    	for(ColorEnum color : order)
    	{
    		Player player = Player.findPlayerByColor(players, color);
    		Card nextCard = player.firstTurn();
    		pickCard(player,1,nextCard);
    	}
    }
    public void lastRound(int nbRound) {
    	for(HashMap<String,Object> map : cardsColumn.get(0))
    	{
    		Player player = Player.findPlayerByColor(players, (ColorEnum)map.get("color"));
    		player.lastTurn(nbRound,(Card)map.get("card"));
    	}
    }
    public void casualRound(int nbRound) {
    	drawCasualRound();
    	//System.out.println(cardsColumn);
    	for(HashMap<String,Object> map : cardsColumn.get(0))
    	{
    		Player player = Player.findPlayerByColor(players, (ColorEnum)map.get("color"));
    		Card nextCard = player.startTurn(nbRound,(Card)map.get("card"));
    		pickCard(player,1,nextCard);
    	}
    }
    public void endGame()
    {
    	System.out.println("Fin du jeux");
		List<Player> playersRanked = ScoreManagement.getLeaderBoard(players);
		System.out.println(players.get(0)+" "+players.get(0).getFinalScore());
		Board.printMatrix(players.get(0).getBoard().getFaceEnum());
		System.out.println(players.get(1)+" "+players.get(1).getFinalScore());
		Board.printMatrix(players.get(1).getBoard().getFaceEnum());
		System.out.println(playersRanked.get(0)+" a gagn� !");
    	// fin de partie
    }
    public List<Card> getCardsAvailable(int columnId) {
    	List<Card> cards = new ArrayList<Card>();
    	for(HashMap<String,Object> map : cardsColumn.get(columnId))
    	{
    		if(map.get("color") == null)
    		{
    			cards.add((Card)map.get("card"));
    		}
    	}
    	return cards;
    }
    public void pickCard(Player player,int columnId,Card card) {
    	for(HashMap<String,Object> map : cardsColumn.get(columnId))
    	{
    		if(map.get("card") == card)
    		{
    			map.put("color", player.getColor());
    		}
    	}    	
    }
    public void removeCardFromColumn(int columnId,Card card)
    {
    	HashMap<String,Object> mapToDelete = null;
    	for(HashMap<String,Object> map : cardsColumn.get(columnId))
    	{
    		if(map.get("card") == card)
    		{
    			mapToDelete = map;
    		}
    	}  
    	cardsColumn.get(columnId).remove(mapToDelete);
    }
    public void clearCardColumn(int columnId) {
    	for(HashMap<String,Object> map : cardsColumn.get(columnId))
    	{
    		cardsList.remove(map.get("card"));
    	}
    	cardsColumn.remove(columnId);
    }
    public int cardsListSize() {
    	return cardsList.size();
    }
    // -------> Utile Pour vous <-------
    // Cette fonction est appell�e avant pickKings et correspond au d�but du premier tour de jeux
    // Valeur Retourn�e : Liste de 2 �lements -> 1 : Premi�re colone de n cartes ( Liste de cartes rang�e par num�ro croissant) , 2 : Deuxi�me colones de n cartes ( Liste de cartes rang�e par num�ro croissant)
    // n -> Nombres de rois totaux dans la partie
    public void drawFirstRound() {
    	cardsColumn.add(createCardColumn());     	// Ajoute la premiere rang�e de cartes
    	cardsColumn.add(createCardColumn());   // Ajoue la deuxi�me rang�e de cartes
    }
    // -------> Inutile Pour vous <-------
    // Cette fonction cr�er une rang�e de cartes
    public List<HashMap<String,Object>> createCardColumn() {
       	List<Card> cards = drawCards();
       	List<HashMap<String,Object>> column = new ArrayList<HashMap<String,Object>>();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	for(Card card : cards)
    	{
    		map = new HashMap<String, Object>();
        	map.put("color",null);
        	map.put("card",card);
        	column.add(map);
    	}
    	return column;
    }
    // -------> Utile Pour vous <-------
    // Cette fonction g�re le d�but de tout les tours de jeux � l'exception du premier (firstRound)
    // Valeur Retourn�e : Liste de 1 �lement -> une colone de n cartes ( Liste de cartes rang�e par num�ro croissant)
    // Cette fonction g�re le d�but de tout les tours de jeux � l'exception du premier (firstRound)
    // Valeur Retourn�e : Liste de 1 �lement -> une colone de n cartes ( Liste de cartes rang�e par num�ro croissant)
    // n -> Nombres de rois totaux dans la partie
    public void drawCasualRound() {
    	cardsColumn.add(createCardColumn()); // Ajoute 
    }
    
    // -------> Utile Pour vous <-------
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
    
    // -------> Inutile pour vous <-------
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
    // -------> Inutile pour vous <-------
    // Cette fonction r�cup�re toutes les cartes du csv et les mets dans une liste
    // Cette fonction r�cup�re toutes les cartes du csv et les mets dans une liste
	public void initCard()
	{
		try {
		BufferedReader reader = new BufferedReader(new FileReader("dominos.csv"));
		String line = null;
		Scanner scanner = null;
		reader.readLine();
		while ((line = reader.readLine()) != null) {
			scanner = new Scanner(line);
			scanner.useDelimiter(",");
			Card card = new Card(Integer.parseInt(scanner.next()),FaceEnum.valueOf(scanner.next()),Integer.parseInt(scanner.next()),FaceEnum.valueOf(scanner.next()),Integer.parseInt(scanner.next()));
			cardsList.add(card);
		}
		reader.close();
		}
		catch(Exception e) {System.out.println(e);};	
	}
    // -------> Inutile pour vous <-------
	// Cette fonction retirer au d�but de la partie un nombre de carte d�pendant du nombre de joueur
	// Cette fonction retirer au d�but de la partie un nombre de carte d�pendant du nombre de joueur
	public void discardCards()
	{
		Random rand = new Random();
		for(int i = 0;i < 48-players.size()*12;i++)
		{
			int n = (int )(Math.random() * cardsList.size());
			cardsList.remove(n);
		}
	}
    // -------> Inutile pour vous <-------
	// Cette fonction pioche n cartes et les regroupent dans une liste de fa�on ordonn�e
	// Cette fonction pioche n cartes et les regroupent dans une liste de fa�on ordonn�e
    // n -> Nombres de rois totaux dans la partie
	public List<Card> drawCards()
	{
		List<Card> cardDrawn = new ArrayList<Card>();
	    Random random = new Random();
	    for(int i = 0;i < numberTotalKings;i++)
	    {
	    	int index = (int )(Math.random() * cardsList.size());
	    	cardDrawn.add(cardsList.get(index));
			cardsList.remove(index);
	    }
	    cardDrawn.sort((card1,card2) -> Integer.compare(card1.getCardId(),card2.getCardId()));
		return cardDrawn;
	}
}

