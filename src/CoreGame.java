import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CoreGame {
	static final int twoPlayerCardCount = 24;
	static final int threePlayerCardCount = 36;
	static final int fourPlayerCardCount = 48;
	List<Player> players;
	List<Card> cardsList;
	List<Color> colorsAvailable;
	int numberTotalKings;
	public CoreGame()
	{
		this.players = new ArrayList<Player>();
		this.cardsList = new ArrayList<>();
		this.colorsAvailable = new ArrayList<>(Arrays.asList(Color.pink,Color.yellow,Color.green,Color.blue));
	}
	public void initGame() {
		initCardsList();
		initPlayers(4);
		drawCards();
	}
	public void firstRound()
	{
	}
	public void prepareRound()
	{
		
	}
	public void playRound()
	{
		
	}
	public void initPlayers(int numberPlayers)
	{
		int numberKings = 1;
		if(numberPlayers == 2)
		{
			numberKings = 2;
			this.numberTotalKings = 2*numberPlayers;
		}
		else
		{
			this.numberTotalKings = 1*numberPlayers;
		}
		for(int i = 0;i < numberPlayers;i++)
		{
			Random rand = new Random();
			Color color = colorsAvailable.get(rand.nextInt(colorsAvailable.size()));
			Player player = new Player(color,numberKings);
			colorsAvailable.remove(color);
			players.add(player);
		}
	}
	public void initCardsList()
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
