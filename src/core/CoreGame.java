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

import core.Player;
import entities.Card;
import entities.Castle;
import entities.Entity;
import entities.Face;
import enums.ColorEnum;
import enums.FaceEnum;
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
    public CoreGame() {
        this.players = new ArrayList<Player>();
        this.cardsList = new ArrayList<>();
        this.colorsAvailable = new ArrayList<>(Arrays.asList(ColorEnum.pink,ColorEnum.yellow,ColorEnum.green,ColorEnum.blue));
        initCard();
        initGame(2);
        players.get(0).board.setCard(0, 0, 0, 1, cardsList.get(8));
        players.get(0).board.setCard(1, 0, 1, 1, cardsList.get(13));
        players.get(0).board.setCard(2, 2, 3, 2, cardsList.get(10));
        players.get(0).board.setCastle(0,2);
        calculateScore();
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
        // D�marrer le jeux ( lancer l'affichage des terrains)
    }
    // -------> Utile Pour vous <-------
    // Cette fonction est appell�e avant pickKings et correspond au d�but du premier tour de jeux
    // Valeur Retourn�e : Liste de 2 �lements -> 1 : Premi�re colone de n cartes ( Liste de cartes rang�e par num�ro croissant) , 2 : Deuxi�me colones de n cartes ( Liste de cartes rang�e par num�ro croissant)
    // n -> Nombres de rois totaux dans la partie
    public void firstRound() {
    	List<List<Card>> cardsColumn = new ArrayList<List<Card>>();	
    	cardsColumn.add(createLine());     	// Ajoute la premiere rang�e de cartes
    	cardsColumn.add(createLine());   // Ajoue la deuxi�me rang�e de cartes
    }
    // -------> Inutile Pour vous <-------
    // Cette fonction cr�er une rang�e de cartes
    public List<Card> createLine() {
    	List<Card> cards = drawCards();
    	return cards;
    }
    // -------> Utile Pour vous <-------
    // Cette fonction g�re le d�but de tout les tours de jeux � l'exception du premier (firstRound)
    // Valeur Retourn�e : Liste de 1 �lement -> une colone de n cartes ( Liste de cartes rang�e par num�ro croissant)
    // n -> Nombres de rois totaux dans la partie
    public void CasualRound() {
    	List<List<Card>> cardsColumn = new ArrayList<List<Card>>();
    	cardsColumn.add(createLine()); // Ajoute 
    }
    
    // -------> Utile Pour vous <-------
    // Cette fonction g�re l'ordre de jeu du premier tour en respectant l'ordre de pioche al�atoire des n rois
    // Valeur Retourn�e : Liste ordonn�es de n couleur correspondant au rois des joueurs respectifs -> Ainsi le premier �lement de la liste est le premier roi pioch� et donc le joueur de cette couleur commencera etc.
    // n -> Nombres de rois totaux dans la partie
    public List<ColorEnum> pickKings()
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
    public void initPlayers(int numberPlayers, int numberKings) {
		for(int i = 0;i < numberPlayers;i++)
		{
			Random rand = new Random();
			ColorEnum color = colorsAvailable.get(rand.nextInt(colorsAvailable.size()));
			Player player = new Player(color,numberKings);
			this.kingsList.put(color, numberKings);
			colorsAvailable.remove(color);
			players.add(player);
		}
    }
    // -------> Inutile pour vous <-------
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
	public void removeCard(int cardId)
	{
		cardsList.remove(cardId);
	}
    // -------> Inutile pour vous <-------
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
    // n -> Nombres de rois totaux dans la partie
	public List<Card> drawCards()
	{
		List<Card> cardDrawn = new ArrayList<Card>();
	    Random random = new Random();
		int[] indexList = random.ints(0, cardsList.size()).distinct().limit(numberTotalKings).sorted().toArray();
		for(int index : indexList)
		{
			cardDrawn.add(cardsList.get(index));
		}
		cardsList.remove(indexList);
		return cardDrawn;
	}
	public void calculateScore()
	{
		for(Player player : players) {
			List<Integer> regionScoreInfo = new ArrayList<Integer>();
			int playerScore = 0;
			int playerLargestDomain = 0;
			int playerNbCouronnes = 0;
			for (FaceEnum faceType : FaceEnum.values()) {
				 regionScoreInfo =calculateRegion(player,faceType);
					if((int)regionScoreInfo.get(0) > playerLargestDomain)
					{
						playerLargestDomain= (int)regionScoreInfo.get(0);
					}
					playerNbCouronnes+= (int)regionScoreInfo.get(0);
					playerScore+= (int)regionScoreInfo.get(0) * (int)regionScoreInfo.get(1);			 
			}
			player.setFinalScore(playerScore, playerLargestDomain, playerNbCouronnes);
		}
	}
	public List<Integer> calculateRegion(Player player,FaceEnum faceType)
	{
		List<Map<String,Integer>> sameType = player.board.getSameType(faceType);
		//System.out.println(faceType+" "+sameType);
		Iterator<Map<String, Integer>> itr = sameType.iterator();
		List<Entity> faceCounted = new ArrayList<Entity>();
		List<Integer> regionScoreInfo = new ArrayList<Integer>();
		int regionScore = 0;
		int regionLargestDomain = 0;
		int regionNbCouronnes = 0;
		while(itr.hasNext())
		{
			Map<String,Integer> caseRef = itr.next();
			List<Object> domainCounted = checkAdjacent(player,faceType,caseRef.get("x"),caseRef.get("y"),faceCounted,null);
			System.out.println("DOmaine counted" +domainCounted);
			//faceCounted = ((List<Entity>)domainCounted.get(2));
			if((int)domainCounted.get(0) > regionLargestDomain)
			{
				regionLargestDomain = (int)domainCounted.get(0);
			}
			regionNbCouronnes+= (int)domainCounted.get(0);
			regionScore+= (int)domainCounted.get(0) * (int)domainCounted.get(1);
		}
		regionScoreInfo.add(regionScore);
		regionScoreInfo.add(regionLargestDomain);
		regionScoreInfo.add(regionNbCouronnes);
		return regionScoreInfo;
	}
	public List<Object> checkAdjacent(Player player,FaceEnum faceRef,int x,int y,List<Entity> faceCounted,Entity previous)
	{
		List<Object> temp;
		Entity entity = player.board.getEntity(x, y);
		List<Object> count = new ArrayList<Object>();
		count.add(0);
		count.add(0);
		count.add(faceCounted);
		//System.out.println("Entity "+entity+" / Previous "+previous);
		if(entity instanceof Face && !faceCounted.contains(entity))
		{
			Entity entityUp = player.board.getEntity(x, y+1);
			Entity entityDown = player.board.getEntity(x, y-1);
			Entity entityLeft = player.board.getEntity(x-1, y);
			Entity entityRight = player.board.getEntity(x+1, y);
			if(entityUp != null && entityUp instanceof Face && ((Face) entityUp).getFaceType() == faceRef && entityUp != previous)
			{
				temp = checkAdjacent(player,faceRef,x,y+1,faceCounted,entity);
				count.set(0,(int)temp.get(0));
				count.set(1,(int)temp.get(1));
				count.set(2,temp.get(2));
			}
			if(entityDown != null && entityDown instanceof Face && ((Face) entityDown).getFaceType() == faceRef && entityDown != previous)
			{
				temp = checkAdjacent(player,faceRef,x,y-1,faceCounted,entity);
				count.set(0,(int)temp.get(0));
				count.set(1,(int)temp.get(1));
				count.set(2,temp.get(2));
			}
			if(entityLeft != null && entityLeft instanceof Face && ((Face) entityLeft).getFaceType() == faceRef && entityLeft != previous)
			{
				temp = checkAdjacent(player,faceRef,x-1,y,faceCounted,entity);
				count.set(0,(int)temp.get(0));
				count.set(1,(int)temp.get(1));
				count.set(2,temp.get(2));
			}
			if(entityRight != null && entityRight instanceof Face && ((Face) entityRight).getFaceType() == faceRef && entityRight != previous)
			{
				temp = checkAdjacent(player,faceRef,x+1,y,faceCounted,entity);
				count.set(0,(int)temp.get(0));
				count.set(1,(int)temp.get(1));
				count.set(2,temp.get(2));
			}
			count.set(0,1+(int)count.get(0));
			count.set(1,((Face)player.board.getEntity(x, y)).getCrown() + (int)count.get(1));
			((List<Entity>)count.get(2)).add(entity);
		}
		return count;
	}
}

