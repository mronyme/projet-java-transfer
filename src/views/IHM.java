package views;

import core.CoreGame;
import entities.Card;
import entities.Castle;
import entities.Entity;
import entities.Face;
import entities.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.*;

public class IHM extends JFrame implements ActionListener {
    JPanel maincontainer = new JPanel();
    JPanel secondcontainer = new JPanel();

    ScreenSize ScreenSize = new ScreenSize();
    JRadioButton player3, player2, player4;
    String text = "Bienvenue dans Domi'Nations, choisissez le nombre de joueurs ci-dessous :";
    public JButton[][] squares = new JButton[9][9];
    JLabel Box1 = new JLabel(" " + text + " ");
    JButton buttonOne = new JButton(" Play !");
    int chateauColonne = 4;
    int chateauLigne = 4;
    int nbPlayer;
    BlockingQueue<Boolean> turnFinished;
    JButton finishTurn;
    private CoreGame game;

    public IHM(CoreGame game) { // Constructeur de la classe IHM

        //D?finit un titre pour notre fen?tre
        this.game = game;
        this.setTitle("Domi'Nations par Arnaud, Baptiste, Chaimaa");
        // On r?cup?re la taille de l'?cran utile, pour cela on utilise l'objet ScreenSize

        int width = ScreenSize.getwidth(); // R?cup?ration de la largeur
        int height = ScreenSize.getheight(); // R?cup?ration de la hauteur

        // Puis on redimensionne la JFrame en fonction

        this.setSize(ScreenSize.getwidth() - 100, ScreenSize.getheight() - 100);
        //Nous demandons maintenant � notre objet de se positionner au centre
        this.setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Et enfin, la rendre visible


        // Composantes de la JFrame
        //Instanciation d'un objet JPanel qui peut contenir des composants ou d'autres conteneurs.

        // JPanel container2 = new JPanel();
        //D�finition de sa couleur de fond
        maincontainer.setBackground(new Color(255, 255, 255, 150));


        //On pr�vient notre JFrame que notre JPanel sera son content pane
        this.setContentPane(new Panneau());
        // Menu Bar
        JMenuBar MenuBar = new JMenuBar();
        MenuBar.setOpaque(true);
        MenuBar.setBackground(new Color(255, 255, 255));
        MenuBar.setPreferredSize(new Dimension(this.getWidth(), 20));
        // Onglets du menu :
        JMenu Accueil = new JMenu("Accueil");
        String str1 = "Param�tres";
        JMenu Parametres = new JMenu(str1);
        JMenu Aide = new JMenu("Aide");

        MenuBar.add(Accueil);
        MenuBar.add(Parametres);
        MenuBar.add(Aide);
        // Option du menu param�tres :
        JMenuItem restart = new JMenuItem("Nouvelle partie");
        restart.addActionListener(new RestartListener());
        Parametres.add(restart);
        // JMenuItem fullscreen = new JMenuItem("Plein �cran");
        //fullscreen.addActionListener(new fullscreen());
        //Parametres.add(fullscreen);

        // Bouton Play
        buttonOne.addActionListener(this);

        // JLabel
        Box1.setHorizontalAlignment(SwingConstants.CENTER);
        Box1.setVerticalAlignment(SwingConstants.CENTER);
        Box1.setOpaque(true);
        Box1.setBackground(new Color(255, 255, 255, 5));
        Box1.setPreferredSize(new Dimension(this.getWidth() / 4, this.getHeight() / 4));

        player2 = new JRadioButton("2 joueurs");
        player3 = new JRadioButton("3 joueurs");
        player4 = new JRadioButton("4 joueurs");
        ButtonGroup bg = new ButtonGroup();
        bg.add(player2);
        bg.add(player3);
        bg.add(player4);


        //Ajout des objets pr�c�dements cr�� dans la fen�tre.
        this.setJMenuBar(MenuBar);
        maincontainer.add(Box1, BorderLayout.NORTH);
        maincontainer.add(player2, BorderLayout.SOUTH);
        maincontainer.add(player3, BorderLayout.SOUTH);
        maincontainer.add(player4, BorderLayout.SOUTH);
        maincontainer.add(buttonOne, BorderLayout.SOUTH);
        this.getContentPane().add(maincontainer, BorderLayout.WEST);

        //Display the window.

        this.setVisible(true);
    }

    public void Grille(ButtonHandler buttonHandler, Player player, int nb_joueurs) {
        maincontainer.setLayout(new GridLayout(9, 9));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                squares[i][j] = new JButton();
            	Entity entity = player.getBoard().getEntity(i,j);
            	if(entity instanceof Face)
            	{
            		ImageIcon icon = new ImageIcon("src/images/"+((Face)entity).getType()+".png");
            		squares[i][j].setIcon(icon);
            	}
            	else if(entity instanceof Castle)
            	{
            		ImageIcon icon = new ImageIcon("chateau.png");
            		squares[i][j].setIcon(icon);

            	}
            	else
            	{
	                String couleur = String.valueOf(player.getColor());
	                switch (couleur) {
	
	                    case "blue":
	                        squares[i][j].setBackground(Color.blue);
	                        break;
	
	                    case "pink":
	                        squares[i][j].setBackground(Color.pink);
	                        break;
	
	                    case "cyan":
	                        squares[i][j].setBackground(Color.cyan);
	                        break;
	                    case "red":
	                        squares[i][j].setBackground(Color.red);
	                        break;
	
	                    default:
	                        System.out.println("Erreur s�lection couleur");
	
	
	                }
            	}
                squares[i][j].setHorizontalTextPosition(JButton.CENTER);
                squares[i][j].setForeground(Color.WHITE);

                squares[i][j].addActionListener(buttonHandler);
                squares[i][j].setName(i + "" + j);
                squares[i][j].setText("L" + i + "C" + j);


                maincontainer.add(squares[i][j]);
            }

        }
        /*switch (nb_joueurs) {

            case 2:

                System.out.println("Nombre de joueurs : 2");
                for (int i = 9; i < 9; i++) {
                    for (int j = 9; j < 9; j++) {
                        squares[i][j].setBackground(Color.pink);
                    }

                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        squares[i][j].setBackground(Color.BLUE);
                    }

                }

                break;

            case 3:

                System.out.println("Nombre de joueurs : 3");

                break;

            case 4:

                System.out.println("Nombre de joueurs : 4");

                break;

            default:

                System.out.println("Nombre de joueurs invalide");

        }*/
    }
    public void aside(Player player,int nbRound) {
        JLabel jlabel = new JLabel("Round: " + nbRound + " | Tour du joueur : " + player.getColor());
        JPanel cardPanel = new JPanel();
    	finishTurn = new JButton("Finir le tour");
        secondcontainer.setBackground(new Color(93, 93, 93));
        secondcontainer.setPreferredSize(new Dimension(this.getWidth(),this.getHeight() ));
        secondcontainer.add(jlabel);
        secondcontainer.add(finishTurn);
        secondcontainer.add(cardPanel,BorderLayout.SOUTH);
        this.getContentPane().add(secondcontainer, BorderLayout.EAST);
        finishTurn.addActionListener(new ActionListener() {
        	  public void actionPerformed(ActionEvent e)
        	  {
        		  if(game.getCardsColumnSize() > 1 && game.getCardsAvailable(1).size() > 0)
        		  {
        			  game.pickCard(player, 1, game.getCardsAvailable(1).get(0));
        		  }
        		  if(game.getRound() == 1)
        		  {
        			  game.pickCard(player, 0, game.getCardsAvailable(0).get(0));
        		  }
        		  Player nextPlayer =  game.getNextPlayer();
        		  if(nextPlayer != null)
        		  {
        			  nextPlayer.casualTurn();
        		  }
        	  }
        });
    }

    // ---------------------------------------------------------------------------------------------------------------------
    public Boolean renderBoard (Player player,int nbRound)  {
    	System.out.println("nbres des joueurs est 2");
        //nouveau plateau pour commencer le jeu
        this.setTitle("Domi'Nations pour " + nbPlayer + " joueurs");

        this.setLocationRelativeTo(null);
        maincontainer.removeAll();
        secondcontainer.removeAll();
        maincontainer.setPreferredSize(new Dimension(this.getWidth() / 2, this.getHeight() - 200));
        //On d�finit le layout � utiliser sur le content pane
        // Ajout de la grille

        ButtonHandler buttonHandler = new ButtonHandler();
        Grille(buttonHandler, player, 2);
        aside(player,nbRound);
        this.setVisible(true);
        return true;
    }


    // ---------------------------------------------------------------------------------------------------------------------
    @Override

    public void actionPerformed(ActionEvent e) {

        if (player2.isSelected()) {
        	nbPlayer = 2;
			game.initGame(2, false, false, false, false);
			renderBoard(game.getFirstPlayer(),1);


        } else if (player3.isSelected()) {
        	nbPlayer = 3;
			game.initGame(3, false, false, false, false);

        } else if (player4.isSelected()) {
        	nbPlayer = 4;
			game.initGame(4, false, false, false, false);
        }
    }

    public class RestartListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            IHM fenetre = new IHM(game);
            System.out.println("Restart");

        }

    }

    public class fullscreen extends JPanel implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {

            this.setSize(ScreenSize.getwidth(), ScreenSize.getheight());
            System.out.println("full screen");
        }

    }
    class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (source == squares[i][j]) {
                        System.out.println("Le bouton cliqu� a pour coordon�e L" + i + "C" + j);
                    }
                }
            }


        }
    }
}



