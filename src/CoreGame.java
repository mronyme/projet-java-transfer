import java.util.ArrayList;
import java.util.List;

public class CoreGame {
	static final int twoPlayerCardCount = 24;
	static final int threePlayerCardCount = 36;
	static final int fourPlayerCardCount = 48;
	List<Player> players;
	Color[] colorAvalaible;
	int numberTotalKings;
	public CoreGame()
	{
		this.players = new ArrayList<Player>();
		this.colorAvalaible = new Color[]{Color.pink,Color.yellow,Color.green,Color.blue};
	}
	public void initGame() {
		int numberPlayers = 0;
		if(numberPlayers == 2)
		{
			this.numberTotalKings = 2*numberPlayers;
			initPlayers(numberPlayers,2);
		}
		else
		{
			this.numberTotalKings = 1*numberPlayers;
			initPlayers(numberPlayers,1);
		}
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
	public void initPlayers(int numberPlayers,int numberKings)
	{
		for(int i = 0;i < numberPlayers;i++)
		{
			Player player = new Player(Color.green,numberKings);
			players.add(player);
		}
	}
	public void drawCards()
	{
		
	}
}
