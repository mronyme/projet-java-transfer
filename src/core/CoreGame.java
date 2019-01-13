package core;

import entities.Board;
import entities.Card;
import entities.Human;
import entities.Player;
import enums.ColorEnum;
import views.IHM;

import java.util.*;

public class CoreGame {
    static final int twoPlayerCardCount = 24; // Nb carte de d�part pour deux joueurs
    static final int threePlayerCardCount = 36; // Nb carte de d�part pour trois joueurs
    static final int fourPlayerCardCount = 48; // Nb carte de d�part pour quatre joueurs
    HashMap<String,Boolean> gameOptions;
    List<Player> players;       //Liste des joueurs
    List<Player> playersOrder;
    int round;
    List<ColorEnum> colorsAvailable;     // Liste des couleurs disponibles lors de la répartition des couleurs pour chaque joueur
    Map<ColorEnum, Integer> kingsList;     // Liste des rois restant pour chaque couleur à piocher lors du premier tour
    int numberTotalKings; // Nombre de rois totaux présent dans la partie
    CardManagement cardManagement;
    IHM window;
    public CoreGame() {
    	this.gameOptions = new HashMap<String,Boolean>();
    	gameOptions.put("dynastyOption", false);
    	gameOptions.put("middleKingdomOption", false);
    	gameOptions.put("harmonyOption", false);
    	gameOptions.put("bigDuelOption", false);
        this.players = new ArrayList<Player>();
        this.colorsAvailable = new ArrayList<>(Arrays.asList(ColorEnum.pink, ColorEnum.cyan, ColorEnum.red, ColorEnum.blue));
        this.cardManagement = new CardManagement(this);
        this.kingsList = new HashMap<ColorEnum, Integer>();
        cardManagement.initCard();
    }
    // Cette fonction g�re le commencement de la partie
    // Valeur en entr�e : nombre de joueurs
    public void initGame(int numberPlayers,Boolean dynastyOption,Boolean middleKingdomOption,Boolean harmonyOption,Boolean bigDuelOption) {
        if(dynastyOption)
        {
        	gameOptions.put("dynastyOption", true);
        }
    	if(middleKingdomOption)
        {
        	gameOptions.put("middleKingdomOption", true);
        }
        if(harmonyOption)
        {
        	gameOptions.put("harmonyOption", true);
        }
        if(bigDuelOption && numberPlayers == 2)
        {
        	gameOptions.put("bigDuelOption", true);
        }
        else
        {
            cardManagement.discardCards(numberPlayers);
        }
    	if (numberPlayers == 2) {
            this.numberTotalKings = 2 * numberPlayers;
            cardManagement.setNumberTotalKings(this.numberTotalKings);
            initPlayers(numberPlayers, 2);
        } else {
            this.numberTotalKings = numberPlayers;
            cardManagement.setNumberTotalKings(this.numberTotalKings);
            initPlayers(numberPlayers, 1);
        }
    	playersOrder = new ArrayList<Player>();
		playersOrder.addAll(firstRoundOrder());
		round = 1;
        //manageRound(0);
    }
    public Player getFirstPlayer() {
    	return playersOrder.get(0);
    }
    public Player getNextPlayer()
    {
    	if(playersOrder.size() > 0)
    	{
    		playersOrder.remove(0);
    	}
    	if(playersOrder.size() == 0 && cardManagement.getCardsListSize() != 0)
    	{
    		this.round+=1;
    		playersOrder.addAll(casualRoundOrder());
    		cardManagement.clearCardColumn(0);	
    		System.out.println(playersOrder);
    	}
    	else if(playersOrder.size() == 0)
    	{
    		if(cardManagement.getCardsColumnSize() != 0)
    		{
    			this.round+=1;
        		playersOrder.addAll(lastRoundOrder());
        		cardManagement.clearCardColumn(0);
    		}
    		else
    		{
    			System.out.println("Jeux fini");
    			return null;
    		}
    	}
    	return playersOrder.get(0);
    }
    // Fonction gérant les tours de jeux jusqu'à la fin
    public void manageRound(int nbRound) {
    	if(cardManagement.getCardsListSize() != 0)
    	{
    		// Déclenche phase préparation 3.1
    		if(nbRound == 0)
    		{
    			initRound();
    		}
    		else
    		{
    			// Déclenche phase premier tour 3.2
	    		if(nbRound == 1)
	    		{
	    			firstRound();
	    		}
	    		// Déclenche tour de jeux habituels 3.3
	    		else
	    		{
	    			casualRound(nbRound);
	    		}
	    		// Retire la première ligne de domino ( qui a été utilisé pendant le tour)
	    		cardManagement.clearCardColumn(0);
    		}
    		// Déclenche le tour suivant
    		manageRound(nbRound+1);
    	}
    	// Si il reste une ligne de domino à posé , on enclenche le dernier tour
    	else if(cardManagement.getCardsColumnSize()  != 0)
    	{
    		lastRound(nbRound);
    		cardManagement.clearCardColumn(0);
    		// fonction gérant la fin du jeux
    		endGame();
    	}
    }
    public void initRound() {
    	for(Player player : players) {
    		player.initTurn();
    	}
    }
    public List<Player> firstRoundOrder() {
    	List<Player> playersOrder = new ArrayList<Player>();
    	List<ColorEnum> order = drawKings();
    	cardManagement.drawFirstRound();
    	for(ColorEnum color : order)
    	{
    		Player player = Player.findPlayerByColor(players, color);
    		playersOrder.add(player);
    	}
    	return playersOrder;
    }
    public List<Player> lastRoundOrder() {
    	List<Player> playersOrder = new ArrayList<Player>();
    	for(HashMap<String,Object> map : cardManagement.getCardsColumn().get(0))
    	{
    		Player player = Player.findPlayerByColor(players, (ColorEnum)map.get("color"));
    		playersOrder.add(player);
    	}
    	return playersOrder;
    }
    public List<Player> casualRoundOrder() {
    	List<Player> playersOrder = new ArrayList<Player>();
    	System.out.println(cardManagement.getCardsColumn());
    	cardManagement.drawCasualRound();
    	for(HashMap<String,Object> map : cardManagement.getCardsColumn().get(1))
    	{
    		Player player = Player.findPlayerByColor(players, (ColorEnum)map.get("color"));
    		playersOrder.add(player);
    	}
    	return playersOrder;
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
		List<Player> playersRanked = ScoreManagement.getLeaderBoard(players,gameOptions);
    	for(Player player : players)
    	{
    		System.out.println(player+" "+player.getFinalScore());
    		Board.printMatrix(player.getBoard().getFaceEnum());    		
    	}
		System.out.println(playersRanked.get(0)+" a gagné !");
    	// fin de partie
    } 
    public List<Card> getCardsAvailable(int columnId) {
    	return cardManagement.getCardsAvailable(columnId);
    }
    public Card getCardToPlay(Player player) {
    	return cardManagement.getCardToPlay(player);
    }
    public int getCardsColumnSize() {
    	return cardManagement.getCardsColumnSize();
    }
	public List<List<HashMap<String,Object>>> getCardsColumn(){
		return cardManagement.getCardsColumn();
	}
    public void pickCard(Player player,int columnId,Card card) {
    	cardManagement.pickCard(player, columnId, card);
    }
    // Cette fonction gère l'ordre de jeu du premier tour en respectant l'ordre de pioche aléatoire des n rois
    // Valeur Retournée : Liste ordonnées de n couleur correspondant au rois des joueurs respectifs -> Ainsi le premier ï¿½lement de la liste est le premier roi piochï¿½ et donc le joueur de cette couleur commencera etc.
    // Cette fonction gère l'ordre de jeu du premier tour en respectant l'ordre de pioche aléatoire des n rois
    // Valeur Retournée : Liste ordonnées de n couleur correspondant au rois des joueurs respectifs -> Ainsi le premier élement de la liste est le premier roi pioché et donc le joueur de cette couleur commencera etc.
    // n -> Nombres de rois totaux dans la partie
    public List<ColorEnum> drawKings()
    {
		Random rand = new Random();
		List<ColorEnum> kingsPicked = new ArrayList<ColorEnum>();
    	for(int i = 0;i < numberTotalKings;i++)
    	{

			ColorEnum[] kingsRemaining = (kingsList.keySet().toArray(new ColorEnum[kingsList.size()]));
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
    
    // Cette fonction instantie les différents joueurs
    // Cette fonction instantie les différents joueurs
    public void initPlayers(int numberPlayers, int numberKings) {
		for(int i = 0;i < numberPlayers;i++)
		{
			Random rand = new Random();
			ColorEnum color = colorsAvailable.get(rand.nextInt(colorsAvailable.size()));
			Player player;
			player = new Human(this,color,numberKings);	
			this.kingsList.put(color, numberKings);
			colorsAvailable.remove(color);
			players.add(player);
		}
    }
    public HashMap<String,Boolean> getGameOptions(){
    	return this.gameOptions;
    }
    public IHM getIHM() {
    	return this.window;
    }
    public void setIHM(IHM window) {
    	this.window = window;
    }
    public int getRound() {
    	return this.round;
    }
}

