package core;

import entities.Card;
import entities.Player;
import enums.FaceEnum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class CardManagement {
	private CoreGame game;
	private List<Card> cardsList;
	private List<List<HashMap<String,Object>>> cardsColumn;
    int numberTotalKings; // Nombre de rois totaux pr�sent dans la partie
	public CardManagement(CoreGame game) {
		this.game = game;
		this.cardsList = new ArrayList<>();
		this.cardsColumn = new ArrayList<List<HashMap<String,Object>>>();
	}
	public void setNumberTotalKings(int number)
	{
		this.numberTotalKings = number;
	}
	public List<List<HashMap<String,Object>>> getCardsColumn(){
		return cardsColumn;
	}
	public int getCardsListSize() {
		return cardsList.size();
	}
	public int getCardsColumnSize() {
		return cardsColumn.size();
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
	    public Card getCardToPlay(Player player) {
	    	for(HashMap<String,Object> map : cardsColumn.get(0))
	    	{
	    		if(map.get("color") == player.getColor() && (Boolean)map.get("used") == false)
	    		{
	    			map.put("used",true);
	    			return (Card)map.get("card");
	    		}
	    	}  
	    	return null;
	    }
	    public void clearCardColumn(int columnId) {
	    	for(HashMap<String,Object> map : cardsColumn.get(columnId))
	    	{
	    		cardsList.remove(map.get("card"));
	    	}
	    	cardsColumn.remove(columnId);
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
	        	map.put("used",false);
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
	    //-------> Inutile pour vous <-------
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
			} catch (Exception e) {
                System.out.println(e);
            }
		}
	    // -------> Inutile pour vous <-------
		// Cette fonction retirer au d�but de la partie un nombre de carte d�pendant du nombre de joueur
		// Cette fonction retirer au d�but de la partie un nombre de carte d�pendant du nombre de joueur
		public void discardCards(int numberPlayer)
		{
			Random rand = new Random();
			if(game.getGameOptions().get("bigDuelOption") == false)
			{
				for(int i = 0;i < 48-numberPlayer*12;i++)
				{
					int n = (int )(Math.random() * cardsList.size());
					cardsList.remove(n);
			}
			}
			/*
			for(int i = 0;i < 48-8;i++)
			{
				int n = (int )(Math.random() * cardsList.size());
				cardsList.remove(n);
			}
			*/
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
