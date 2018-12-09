import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoreGame {
    static final int twoPlayerCardCount = 24; // Nb carte de départ pour deux joueurs
    static final int threePlayerCardCount = 36; // Nb carte de départ pour trois joueurs
    static final int fourPlayerCardCount = 48; // Nb carte de départ pour quatre joueurs
    List<Player> players;
    List<Card> cardsList;
    Color[] colorAvalaible;
    int numberTotalKings;

    public CoreGame() {
        this.players = new ArrayList<Player>();
        this.cardsList = new ArrayList<>();
        this.colorAvalaible = new Color[]{Color.pink, Color.yellow, Color.green, Color.blue};
        try {
            BufferedReader reader = new BufferedReader(new FileReader("dominos.csv"));
            String line = null;
            Scanner scanner = null;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                Card card = new Card(Integer.parseInt(scanner.next()), scanner.next(), Integer.parseInt(scanner.next()), scanner.next(), Integer.parseInt(scanner.next()));
                cardsList.add(card);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        ;

    }

    public void initGame() {


        System.out.print(Plateau.numberPlayers);


        if (Plateau.numberPlayers == 2) {
            this.numberTotalKings = 2 * Plateau.numberPlayers;
            initPlayers(Plateau.numberPlayers, 2);
        } else {
            this.numberTotalKings = Plateau.numberPlayers;
            initPlayers(Plateau.numberPlayers, 1);
        }
    }

    public void firstRound() {
    }

    public void prepareRound() {

    }

    public void playRound() {

    }

    public void initPlayers(int numberPlayers, int numberKings) {
        for (int i = 0; i < numberPlayers; i++) {
            Player player = new Player(Color.green, numberKings);
            players.add(player);
        }
    }

    public void drawCards() {

    }
}
