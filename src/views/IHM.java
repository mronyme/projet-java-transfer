package views;

import core.CoreGame;
import entities.Board;
import entities.Card;
import entities.Castle;
import entities.Entity;
import entities.Face;
import entities.Human;
import entities.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.ArrayList;

public class IHM extends JFrame implements ActionListener {
    ButtonGroup cardGroup = new ButtonGroup();
    JPanel maincontainer = new JPanel();
    JPanel secondcontainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel Border = new JPanel (new FlowLayout(FlowLayout.CENTER));
    ScreenSize ScreenSize = new ScreenSize();
    JRadioButton player3, player2, player4;
    String text = "Bienvenue dans Domi'Nations, choisissez le nombre de joueurs :";
    public JButton[][] squares = new JButton[9][9];
    JLabel Box1 = new JLabel(" " + text + " ");
    JButton buttonOne = new JButton(" Play !");
    JButton finishTurn;
    int nbPlayer;
    int[] checkFinishTurn;
    Card cardPicked;
    JLabel info1;
    JLabel info2;
    Boolean face1Picked; // Face de gauche sélectionnée
    int face1X,face1Y; // Coordonées sur plateau de la face gauche
    int face2X,face2Y; // Coordonées sur plateau de la face droite
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
        //Nous demandons maintenant à notre objet de se positionner au centre
        this.setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Et enfin, la rendre visible


        // Composantes de la JFrame
        //Instanciation d'un objet JPanel qui peut contenir des composants ou d'autres conteneurs.

        // JPanel container2 = new JPanel();
        //Définition de sa couleur de fond
        maincontainer.setBackground(new Color(255, 255, 255, 150));


        //On prévient notre JFrame que notre JPanel sera son content pane
        this.setContentPane(new Panneau());
        // Menu Bar
        JMenuBar MenuBar = new JMenuBar();
        MenuBar.setOpaque(true);
        MenuBar.setBackground(new Color(255, 255, 255));
        MenuBar.setPreferredSize(new Dimension(this.getWidth(), 20));
        // Onglets du menu :
        JMenu Accueil = new JMenu("Accueil");
        String str1 = "Paramètres";
        JMenu Parametres = new JMenu(str1);
        JMenu Aide = new JMenu("Aide");

        MenuBar.add(Accueil);
        MenuBar.add(Parametres);
        MenuBar.add(Aide);
        // Option du menu paramètres :
        JMenuItem restart = new JMenuItem("Nouvelle partie");
        restart.addActionListener(new RestartListener());
        Parametres.add(restart);
        // JMenuItem fullscreen = new JMenuItem("Plein écran");
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


        //Ajout des objets précédements créé dans la fenêtre.
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

    public void Grille(BoardHandler boardHandler, Player player, int nb_joueurs) {
        maincontainer.setLayout(new GridLayout(9, 9));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                squares[i][j] = new JButton();
            	Entity entity = player.getBoard().getEntity(i,j);
            	if(entity instanceof Face)
            	{
            		ImageIcon icon = new ImageIcon("src/images/"+((Face)entity).getFaceType()+".png");
            		squares[i][j].setIcon(icon);
            	}
            	else if(entity instanceof Castle)
            	{
            		ImageIcon icon = new ImageIcon("src/images/chateau.png");
            		squares[i][j].setIcon(icon);

            	}
            	else
            	{
	                String couleur = String.valueOf(player.getColor());
	                switch (couleur) {
	
	                    case "blue":
                            squares[i][j].setBackground(new Color (34,57,196));
	                        break;
	
	                    case "pink":
	                        squares[i][j].setBackground(Color.pink);
	                        break;
	
	                    case "cyan":
	                        squares[i][j].setBackground(Color.cyan);
	                        break;
	                    case "red":
                            squares[i][j].setBackground(new Color (196,57,34));
	                        break;
	
	                    default:
	                        System.out.println("Erreur sélection couleur");

	
	
	                }
            	}
                squares[i][j].setHorizontalTextPosition(JButton.CENTER);
                squares[i][j].setForeground(Color.WHITE);

                squares[i][j].addActionListener(boardHandler);
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
    public void addToDraw(List<Card> cards)
    {
    	cardPanel.removeAll();
    	this.getContentPane().repaint();
    	DrawHandler drawdHandler = new DrawHandler();
        for(Card card : cards)
    	{
    		BufferedImage face1 = null;
    		BufferedImage face2 = null;
			try {
				face1 = ImageIO.read(new File("src/images/"+card.getFace1().getFaceType()+".png"));
				face2 = ImageIO.read(new File("src/images/"+card.getFace2().getFaceType()+".png"));
			} catch (IOException e1) {

				e1.printStackTrace();
			}
    		JButton face1Label = new JButton(new ImageIcon(face1));
    		JButton face2Label = new JButton(new ImageIcon(face2));
    		face1Label.putClientProperty("card", card);
    		face2Label.putClientProperty("card", card);
            face1Label.setBackground(Color.BLACK);
            face2Label.setBackground(Color.BLACK);
            face1Label.setForeground(Color.BLACK);
            face2Label.setForeground(Color.BLACK);
            face1Label.setPreferredSize(new Dimension(this.getWidth()/10,this.getHeight()/10));
            face2Label.setPreferredSize(new Dimension(this.getWidth()/10,this.getHeight()/10));
            face1Label.setAlignmentX(Component.LEFT_ALIGNMENT);
            face2Label.setAlignmentX(Component.RIGHT_ALIGNMENT);
            face1Label.addActionListener(drawdHandler);
            face2Label.addActionListener(drawdHandler);
            cardGroup.add(face1Label);
            cardGroup.add(face2Label);
    		cardPanel.add(face1Label);
    		cardPanel.add(face2Label);
    	}    	
    }
    public void aside(Player player,int nbRound) {
    	info1 = new JLabel("Round: " + nbRound + " | Tour du joueur : " + player.getColor());
    	List<Card> cards = null;
    	if(nbRound == 1)
    	{
    		info2 = new JLabel("Sélectionnez votre première carte");
    		cards= game.getCardsAvailable(0);
    	}
    	else
    	{
    		info2 = new JLabel("Placer votre carte");
    		cards = new ArrayList<Card>();
            cardPicked = game.getCardToPlay(player);
            cards.add(cardPicked);
    	}
    	finishTurn = new JButton("Finir le tour");
    	finishTurn.setBackground(Color.darkGray);
    	finishTurn.setForeground(Color.white);


        cardPanel.setBackground(new Color (255,255,255,10));
        cardPanel.setPreferredSize(new Dimension(this.getWidth()/4,this.getHeight()));
        secondcontainer.setBackground(new Color (255,255,255,200));
        secondcontainer.setPreferredSize(new Dimension(this.getWidth()/5,this.getHeight() ));
        secondcontainer.add(info1);
        secondcontainer.add(info2,BorderLayout.SOUTH);
        secondcontainer.add(finishTurn);
        secondcontainer.add(cardPanel);

        addToDraw(cards);
        finishTurn.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent e)
      	  {
      		  if(checkFinishTurn[0] == 1 && checkFinishTurn[1] == 1)
      		  {
      			  game.pickCard(player, 1, cardPicked);
	      		  Player nextPlayer =  game.getNextPlayer();
	      		  if(nextPlayer != null)
	      		  {
	      			nextPlayer.casualTurn();
	      		  }
	      		  else
	      		  {
	      			  renderLeaderBoard();
	      		  }
      		  }
      	  }
      });
    }
    // ---------------------------------------------------------------------------------------------------------------------
    public void renderBoard (Player player,int nbRound)  {
    	this.checkFinishTurn = new int[] {0,0};
    	this.face1Picked = false;
        //nouveau plateau pour commencer le jeu
        this.setTitle("Domi'Nations pour " + nbPlayer + " joueurs");

        this.setLocationRelativeTo(null);
        this.maincontainer.removeAll();
        this.secondcontainer.removeAll();
        this.getContentPane().removeAll();

        maincontainer.setPreferredSize(new Dimension(this.getWidth() / 2, this.getHeight() - 200));
        //On définit le layout à utiliser sur le content pane
        // Ajout de la grille

        BoardHandler boardHandler = new BoardHandler(player);
        Grille(boardHandler, player, 2);
        aside(player,nbRound);
        this.getContentPane().add(maincontainer, BorderLayout.WEST);
        this.getContentPane().add(secondcontainer, BorderLayout.EAST);
        this.getContentPane().repaint();
        this.setVisible(true);
    }

    public void renderLeaderBoard() {
    	
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
    class BoardHandler implements ActionListener {

    	private Player player;
    	 public BoardHandler(Player player) {
    	        this.player = player;
    	    }
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (source == squares[i][j]) {
                    	if(cardPicked != null)
                    	{
                    		if(face1Picked == false)
                    		{
                    			face1X = i;
                    			face1Y = j;
                    			face1Picked = true;
                    		}
                    		else
                    		{
                    			face2X = i;
                    			face2Y = j;
                    			if(Board.moveIsValid(player, face1X, face1Y, face2X, face2Y, cardPicked))
                    			{
                    				if(checkFinishTurn[0] != 1)
                    				{
                    					squares[face1X][face1Y].setIcon(new ImageIcon("src/images/"+((Face)cardPicked.getFace1()).getFaceType()+".png"));
                        				squares[face2X][face2Y].setIcon(new ImageIcon("src/images/"+((Face)cardPicked.getFace2()).getFaceType()+".png"));
                    					player.getBoard().setCard(face1X, face1Y, face2X, face2Y, cardPicked);
	                    				if(game.getRound() == 1)
	                    				{	
		                    				game.pickCard(player, 0, cardPicked);
	                    				}
	                    				if(game.getCardsColumnSize() != 1)
	                    				{
	                    					
		                    				info2.setText("Sélectionnez la carte suivante à jouer");
		                        			addToDraw(game.getCardsAvailable(1));		                    				
		                        		}
	                    				else
	                    				{
	                    					info2.setText("Appuyer sur le bouton \"Finir le tour \"");
	                    					checkFinishTurn[1] = 1;
	                    				}
	                    				checkFinishTurn[0] = 1;
                    				}
                    			}             
                    			face1Picked = false;
                    		}
                    	}
                    }
                }
            }


        }
    }
    class DrawHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            ((JButton)source).setBorder(BorderFactory.createLineBorder(Color.red));
            cardPicked = (Card)((JButton)source).getClientProperty("card");
            if(checkFinishTurn[0] == 1)
            {
            	info2.setText("Appuyer sur le bouton \"Finir le tour \"");
            	checkFinishTurn[1] = 1;
            }
            else if(game.getRound() == 1) {
            	info2.setText("Placer votre première carte");
            }
        }
    }
}



