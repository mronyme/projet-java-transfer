package Core;

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

import Core.Card;
import Core.Color;
import Core.Player;
import views.Window;

public class CoreGame {
    static final int twoPlayerCardCount = 24; // Nb carte de départ pour deux joueurs
    static final int threePlayerCardCount = 36; // Nb carte de départ pour trois joueurs
    static final int fourPlayerCardCount = 48; // Nb carte de départ pour quatre joueurs
    List<Player> players;
    List<Card> cardsList;
    List<Color> colorsAvailable;
    Map<Color, Integer> kingsList = new HashMap<Color, Integer>();
    int numberTotalKings;
    public CoreGame() {
        this.players = new ArrayList<Player>();
        this.cardsList = new ArrayList<>();
        this.colorsAvailable = new ArrayList<>(Arrays.asList(Color.pink,Color.yellow,Color.green,Color.blue));
    }

    public void initGame(int numberPlayers) {
        if (numberPlayers == 2) {
            this.numberTotalKings = 2 * numberPlayers;
            initPlayers(numberPlayers, 2);
        } else {
            this.numberTotalKings = numberPlayers;
            initPlayers(numberPlayers, 1);
        }
        pickKings();
    }

    public void firstRound() {
    	List<List<Card>> cardsLine = new ArrayList<List<Card>>();
    	cardsLine.add(createLine());
    	cardsLine.add(createLine());
    }

    public List<Card> createLine() {
    	List<Card> cards = drawCards();
    	return cards;
    }

    public void playRound() {
    	List<List<Card>> cardsLine = new ArrayList<List<Card>>();
    	cardsLine.add(createLine());
    }
    public List<Color> pickKings()
    {
		Random rand = new Random();
		List<Color> kingsPicked = new ArrayList<Color>();
    	for(int i = 0;i < numberTotalKings;i++)
    	{
    		
    		Color[] kingsRemaining = (Color[])(kingsList.keySet().toArray(new Color[kingsList.size()]));
    		Color kingColor = kingsRemaining[rand.nextInt(kingsRemaining.length)];
    		kingsList.put(kingColor,kingsList.get(kingColor) -1);
    		if(kingsList.get(kingColor) == 0)
    		{
    			kingsList.remove(kingColor);
    		}
    		kingsPicked.add(kingColor);
    	}
    	return kingsPicked;
    }
    public void initPlayers(int numberPlayers, int numberKings) {
		for(int i = 0;i < numberPlayers;i++)
		{
			Random rand = new Random();
			Color color = colorsAvailable.get(rand.nextInt(colorsAvailable.size()));
			Player player = new Player(color,numberKings);
			this.kingsList.put(color, numberKings);
			colorsAvailable.remove(color);
			players.add(player);
		}
    }
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
	public void removeCard(int cardId)
	{
		cardsList.remove(cardId);
	}
	public void discardCards()
	{
		Random rand = new Random();
		for(int i = 0;i < 48-players.size()*12;i++)
		{
			int n = (int )(Math.random() * cardsList.size());
			cardsList.remove(n);
		}
	}
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

