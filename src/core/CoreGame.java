package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import core.Player;
import entities.Card;
import enums.ColorEnum;
import views.Window;

public class CoreGame {
    static final int twoPlayerCardCount = 24; // Nb carte de départ pour deux joueurs
    static final int threePlayerCardCount = 36; // Nb carte de départ pour trois joueurs
    static final int fourPlayerCardCount = 48; // Nb carte de départ pour quatre joueurs
    
    List<Player> players;       //Liste des joueurs
    List<Card> cardsList;     // Liste des Cartes totales présentent dans le jeux à tout instant
    List<ColorEnum> colorsAvailable;     // Liste des couleurs disponibles lors de la répartition des couleurs pour chaque joueur
    Map<ColorEnum, Integer> kingsList = new HashMap<ColorEnum, Integer>();     // Liste des rois restant pour chaque couleur à piocher lors du premier tour
    int numberTotalKings; // Nombre de rois totaux présent dans la partie
    public CoreGame() {
        this.players = new ArrayList<Player>();
        this.cardsList = new ArrayList<>();
        this.colorsAvailable = new ArrayList<>(Arrays.asList(ColorEnum.pink,ColorEnum.yellow,ColorEnum.green,ColorEnum.blue));
        initCard();
    }
    // -------> Utile Pour vous <-------
    // Cette fonction gère le commencement de la partie
    // Valeur en entrée : nombre de joueurs
    public void initGame(int numberPlayers) {
        if (numberPlayers == 2) {
            this.numberTotalKings = 2 * numberPlayers;
            initPlayers(numberPlayers, 2);
        } else {
            this.numberTotalKings = numberPlayers;
            initPlayers(numberPlayers, 1);
        }
        discardCards();
        // Démarrer le jeux ( lancer l'affichage des terrains)
    }
    // -------> Utile Pour vous <-------
    // Cette fonction est appellée avant pickKings et correspond au début du premier tour de jeux
    // Valeur Retournée : Liste de 2 élements -> 1 : Première colone de n cartes ( Liste de cartes rangée par numéro croissant) , 2 : Deuxième colones de n cartes ( Liste de cartes rangée par numéro croissant)
    // n -> Nombres de rois totaux dans la partie
    public void firstRound() {
    	List<List<Card>> cardsColumn = new ArrayList<List<Card>>();	
    	cardsColumn.add(createLine());     	// Ajoute la premiere rangée de cartes
    	cardsColumn.add(createLine());   // Ajoue la deuxième rangée de cartes
    }
    // -------> Inutile Pour vous <-------
    // Cette fonction créer une rangée de cartes
    public List<Card> createLine() {
    	List<Card> cards = drawCards();
    	return cards;
    }
    // -------> Utile Pour vous <-------
    // Cette fonction gère le début de tout les tours de jeux à l'exception du premier (firstRound)
    // Valeur Retournée : Liste de 1 élement -> une colone de n cartes ( Liste de cartes rangée par numéro croissant)
    // n -> Nombres de rois totaux dans la partie
    public void CasualRound() {
    	List<List<Card>> cardsColumn = new ArrayList<List<Card>>();
    	cardsColumn.add(createLine()); // Ajoute 
    }
    
    // -------> Utile Pour vous <-------
    // Cette fonction gère l'ordre de jeu du premier tour en respectant l'ordre de pioche aléatoire des n rois
    // Valeur Retournée : Liste ordonnées de n couleur correspondant au rois des joueurs respectifs -> Ainsi le premier élement de la liste est le premier roi pioché et donc le joueur de cette couleur commencera etc.
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
    // Cette fonction instantie les différents joueurs
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
			Card card = new Card(Integer.parseInt(scanner.next()),scanner.next(),Integer.parseInt(scanner.next()),scanner.next(),Integer.parseInt(scanner.next()));
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
	// Cette fonction retirer au début de la partie un nombre de carte dépendant du nombre de joueur
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
	// Cette fonction pioche n cartes et les regroupent dans une liste de façon ordonnée
    // n -> Nombres de rois totaux dans la partie
	public List<Card> drawCards()
	{
		List<Card> cardDrawn = new ArrayList<Card>();
	    Random random = new Random();
		int[] indexList = random.ints(0, cardsList.size()).distinct().limit(numberTotalKings).sorted().toArray();
		for(int index : indexList)
		{
			System.out.println(index);
			cardDrawn.add(cardsList.get(index));
		}
		cardsList.remove(indexList);
		return cardDrawn;
	}
}

