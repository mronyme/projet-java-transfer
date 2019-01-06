package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import entities.Card;
import entities.Ia;
import entities.Player;
import enums.ColorEnum;
import enums.FaceEnum;

public class CardManagement {
	private CoreGame game;
	private List<Card> cardsList;
	private List<List<HashMap<String,Object>>> cardsColumn;
    int numberTotalKings; // Nombre de rois totaux présent dans la partie
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
	    public void clearCardColumn(int columnId) {
	    	for(HashMap<String,Object> map : cardsColumn.get(columnId))
	    	{
	    		cardsList.remove(map.get("card"));
	    	}
	    	cardsColumn.remove(columnId);
	    }
	    // -------> Utile Pour vous <-------
	    // Cette fonction est appellée avant pickKings et correspond au début du premier tour de jeux
	    // Valeur Retournée : Liste de 2 élements -> 1 : Première colone de n cartes ( Liste de cartes rangée par numéro croissant) , 2 : Deuxième colones de n cartes ( Liste de cartes rangée par numéro croissant)
	    // n -> Nombres de rois totaux dans la partie
	    public void drawFirstRound() {
	    	cardsColumn.add(createCardColumn());     	// Ajoute la premiere rangée de cartes
	    	cardsColumn.add(createCardColumn());   // Ajoue la deuxième rangée de cartes
	    }
	    // -------> Inutile Pour vous <-------
	    // Cette fonction créer une rangée de cartes
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
	    // Cette fonction gï¿½re le dï¿½but de tout les tours de jeux ï¿½ l'exception du premier (firstRound)
	    // Valeur Retournï¿½e : Liste de 1 ï¿½lement -> une colone de n cartes ( Liste de cartes rangï¿½e par numï¿½ro croissant)
	    // Cette fonction gère le début de tout les tours de jeux à l'exception du premier (firstRound)
	    // Valeur Retournée : Liste de 1 élement -> une colone de n cartes ( Liste de cartes rangée par numéro croissant)
	    // n -> Nombres de rois totaux dans la partie
	    public void drawCasualRound() {
	    	cardsColumn.add(createCardColumn()); // Ajoute 
	    }
	    //-------> Inutile pour vous <-------
	    // Cette fonction rï¿½cupï¿½re toutes les cartes du csv et les mets dans une liste
	    // Cette fonction récupère toutes les cartes du csv et les mets dans une liste
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
		// Cette fonction retirer au dï¿½but de la partie un nombre de carte dï¿½pendant du nombre de joueur
		// Cette fonction retirer au début de la partie un nombre de carte dépendant du nombre de joueur
		public void discardCards(int numberPlayer)
		{
			Random rand = new Random();
			for(int i = 0;i < 48-numberPlayer*12;i++)
			{
				int n = (int )(Math.random() * cardsList.size());
				cardsList.remove(n);
			}
		}
	    // -------> Inutile pour vous <-------
		// Cette fonction pioche n cartes et les regroupent dans une liste de faï¿½on ordonnï¿½e
		// Cette fonction pioche n cartes et les regroupent dans une liste de façon ordonnée
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
