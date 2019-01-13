package views;

import core.CoreGame;
import entities.Board;
import entities.Card;
import entities.Castle;
import entities.Entity;
import entities.Face;
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
import java.util.ArrayList;

public class IHM extends JFrame implements ActionListener {
    JPanel maincontainer = new JPanel();
    JPanel secondcontainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel BorderOne = new JPanel (new FlowLayout(FlowLayout.CENTER));
    JPanel BorderTwo = new JPanel (new FlowLayout(FlowLayout.CENTER));
    JPanel BorderThree = new JPanel (new FlowLayout(FlowLayout.CENTER));
    JPanel BorderFour = new JPanel (new FlowLayout(FlowLayout.CENTER));
    JPanel info = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel infoOne= new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel infoTwo= new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel bContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
    // --
    JButton buttonOne = new JButton(" Play !");
    JButton finishTurn;
    JButton discardCard;
    public JButton[][] zone = new JButton[9][9];
    JRadioButton player3, player2, player4;
   // --
    String text = "Bienvenue dans Domi'Nations, choisissez le nombre de joueurs :";
    JLabel Box1 = new JLabel(" " + text + " ");
    JLabel info1;
    JLabel info2;
    // --
    ScreenSize ScreenSize = new ScreenSize();
    int nbPlayer;
    int[] checkFinishTurn;
    Card cardPicked;
    
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
       // renderLeaderBoard();
    }

    public void Grille(BoardHandler boardHandler, Player player, int nb_joueurs) {
        maincontainer.setLayout(new GridLayout(9, 9));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                zone[i][j] = new JButton();
            	Entity entity = player.getBoard().getEntity(i,j);
            	if(entity instanceof Face)
            	{
            		ImageIcon icon = new ImageIcon("src/images/"+((Face)entity).getFaceType()+".png");
            		zone[i][j].setIcon(icon);
            	}
            	else if(entity instanceof Castle)
            	{
            		ImageIcon icon = new ImageIcon("src/images/chateau.png");
            		zone[i][j].setIcon(icon);

            	}
            	else
            	{
	                String couleur = String.valueOf(player.getColor());
	                switch (couleur) {
	
	                    case "blue":
                            zone[i][j].setBackground(new Color (34,57,196));
	                        break;
	
	                    case "pink":
	                        zone[i][j].setBackground(Color.pink);
	                        break;
	
	                    case "cyan":
                            zone[i][j].setBackground(new Color (13, 162, 196));
	                        break;
	                    case "red":
                            zone[i][j].setBackground(new Color (196,57,34));
	                        break;
	
	                    default:
	                        System.out.println("Erreur sélection couleur");

	
	
	                }
            	}
                zone[i][j].setHorizontalTextPosition(JButton.CENTER);
                zone[i][j].setForeground(Color.WHITE);

                zone[i][j].addActionListener(boardHandler);
                zone[i][j].setName(i + "" + j);
                zone[i][j].setText("L" + i + "C" + j);


                maincontainer.add(zone[i][j]);
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
    	BorderOne.removeAll();
    	BorderTwo.removeAll();
    	BorderThree.removeAll();
    	BorderFour.removeAll();
    	this.getContentPane().repaint();
    	DrawHandler drawdHandler = new DrawHandler();
        int idcarte = 0;
    	for(Card card : cards)
    	{
            idcarte++;
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
            switch (idcarte)

            {

                case 1:
                    BorderOne.add(face1Label);
                    BorderOne.add(face2Label);
            		face1Label.putClientProperty("border", BorderOne);
            		face2Label.putClientProperty("border", BorderOne);
                    cardPanel.add(BorderOne);
                    if(cards.size() == 1)
                    {
                    	if(game.getRound() == 1)
                    	{
                    		info2.setText("Placer votre carte");
                    	}
                        BorderOne.setBackground(new Color (255,255,255));
                        checkFinishTurn[1] = 1;
                        cardPicked = card;
                    }
                    break;

                case 2:
                    BorderTwo.add(face1Label);
                    BorderTwo.add(face2Label);
            		face1Label.putClientProperty("border", BorderTwo);
            		face2Label.putClientProperty("border", BorderTwo);
                    cardPanel.add(BorderTwo);
                    break;

                case 3:
                    BorderThree.add(face1Label);
                    BorderThree.add(face2Label);
            		face1Label.putClientProperty("border", BorderThree);
            		face2Label.putClientProperty("border", BorderThree);
                    cardPanel.add(BorderThree);
                    break;
                case 4:
                    BorderFour.add(face1Label);
                    BorderFour.add(face2Label);
            		face1Label.putClientProperty("border", BorderFour);
            		face2Label.putClientProperty("border", BorderFour);
                    cardPanel.add(BorderFour);
                    break;


                default:

                   System.out.println("No Card available");

                    break;

            }


    	}    	
    }
    public void aside(Player player,int nbRound) {
    	info1 = new JLabel("Round: " + nbRound + " | Tour du joueur : " + player.getColor());
    	List<Card> cards = null;
    	
    	finishTurn = new JButton("Finir le tour");
    	discardCard = new JButton("Défausser la carte");
        bContainer.add(finishTurn);
        bContainer.add(discardCard);
        bContainer.setPreferredSize(new Dimension(this.getWidth()/4-60,this.getHeight()/14 ));
        bContainer.setBackground(new Color(255,255,255,0));
    	discardCard.setBackground(Color.darkGray);
    	discardCard.setForeground(Color.white);
    	finishTurn.setBackground(Color.darkGray);
    	finishTurn.setForeground(Color.white);
    	
    	if(nbRound == 1)
    	{
    		info2 = new JLabel("Sélectionnez votre première carte");
    		cards= game.getCardsAvailable(0);
			discardCard.setVisible(false);
    	}
    	else
    	{
    		info2 = new JLabel("Placer votre carte");
    		cards = new ArrayList<Card>();
            cardPicked = game.getCardToPlay(player);
            cards.add(cardPicked);
			discardCard.setVisible(true);
    	}

        cardPanel.setBackground(new Color (255,255,255,0));
        secondcontainer.setBackground(new Color (255,255,255,100));
        BorderOne.setBackground(new Color(0,0,0,0));
        BorderTwo.setBackground(new Color(0,0,0,0));
        BorderThree.setBackground(new Color(0,0,0,0));
        BorderFour.setBackground(new Color(0,0,0,0));
        cardPanel.setPreferredSize(new Dimension(this.getWidth()/4,this.getHeight()));
        secondcontainer.setPreferredSize(new Dimension(this.getWidth()/4-60,this.getHeight() ));

        secondcontainer.add(bContainer);
        info.setPreferredSize(new Dimension(this.getWidth()/4-60,this.getHeight()/8 ));
        info.setBackground(new Color (255,255,255,0));
        infoOne.setPreferredSize(new Dimension(this.getWidth()/4-60,this.getHeight()/14 ));
        infoTwo.setPreferredSize(new Dimension(this.getWidth()/4-60,this.getHeight()/14 ));
        infoOne.setBackground(new Color (255,255,255,0));
        infoTwo.setBackground(new Color (255,255,255,0));
       infoOne.add(info1);
       infoTwo.add(info2);
        info.add(infoOne,BorderLayout.NORTH);
        info.add(infoTwo,BorderLayout.SOUTH);
        secondcontainer.add(info);
        secondcontainer.add(bContainer);
        secondcontainer.add(cardPanel);

        addToDraw(cards);
        finishTurn.setVisible(false);
        discardCard.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent e)
      	  {
              BorderOne.setBackground(new Color(0,0,0,0));
              BorderTwo.setBackground(new Color(0,0,0,0));
              BorderThree.setBackground(new Color(0,0,0,0));
              BorderFour.setBackground(new Color(0,0,0,0));
        		checkFinishTurn[0] = 1;
        		checkFinishTurn[2] = 1;
        		if(game.getCardsColumnSize() > 1)
				{
					List<Card> cards = game.getCardsAvailable(1);
					if(cards.size() != 1)
					{
    					info2.setText("Sélectionnez la carte suivante à jouer");	
    					discardCard.setVisible(false);
					}
					else
					{
						info2.setText("Appuyer sur le bouton \"Finir le tour \"");
						finishTurn.setVisible(true);
						discardCard.setVisible(false);
					}
        			addToDraw(cards);	
        		}
				else
				{
					info2.setText("Appuyer sur le bouton \"Finir le tour \"");
					checkFinishTurn[1] = 1;
					finishTurn.setVisible(true);
					discardCard.setVisible(false);
					addToDraw(null);
				}
      	  }
      });
        finishTurn.addActionListener(new ActionListener() {
      	  public void actionPerformed(ActionEvent e)
      	  {
      		  if(checkFinishTurn[0] == 1 && checkFinishTurn[1] == 1)
      		  {
      			 if(game.getCardsColumn().size() != 1)
      			 {
      				 game.pickCard(player, 1, cardPicked);
      			 }
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
    	this.cardPicked = null;
    	this.checkFinishTurn = new int[] {0,0,0};
    	this.face1Picked = false;
        //nouveau plateau pour commencer le jeu
        this.setTitle("Domi'Nations pour " + nbPlayer + " joueurs");

        this.setLocationRelativeTo(null);
        this.maincontainer.removeAll();
        this.info.removeAll();
        this.infoOne.removeAll();
        this.infoTwo.removeAll();
        this.secondcontainer.removeAll();
        info.removeAll();
        this.getContentPane().removeAll();
        this.bContainer.removeAll();

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
     // renderLeaderBoard();
    }

    // ---------------------------------------------------------------------------------------------------------------------
    public void renderLeaderBoard() {
    	getContentPane().removeAll();
        this.setContentPane(new Panneau());

        JPanel leaderBoard= new JPanel(new FlowLayout(FlowLayout.CENTER));
leaderBoard.setBackground(new Color( 3,3,3,200));
        GridBagLayout gridLayout;
        gridLayout = new GridBagLayout();
leaderBoard.setPreferredSize(new Dimension(200,200));
        //Setting the layout manager for our container (in this case the JPanel)
        leaderBoard.setLayout(gridLayout);
        getContentPane().add(leaderBoard);


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
                    if (source == zone[i][j]) {
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
                    		            BorderOne.setBackground(new Color(0,0,0,0));
                    		            BorderTwo.setBackground(new Color(0,0,0,0));
                    		            BorderThree.setBackground(new Color(0,0,0,0));
                    		            BorderFour.setBackground(new Color(0,0,0,0));
                    					zone[face1X][face1Y].setIcon(new ImageIcon("src/images/"+((Face)cardPicked.getFace1()).getFaceType()+".png"));
                        				zone[face2X][face2Y].setIcon(new ImageIcon("src/images/"+((Face)cardPicked.getFace2()).getFaceType()+".png"));
                    					player.getBoard().setCard(face1X, face1Y, face2X, face2Y, cardPicked);
	                    				if(game.getRound() == 1)
	                    				{	
		                    				game.pickCard(player, 0, cardPicked);
	                    				}
	                    				if(game.getCardsColumnSize() > 1)
	                    				{
	                    					List<Card> cards = game.getCardsAvailable(1);
	                    					if(cards.size() != 1)
	                    					{
		                    					info2.setText("Sélectionnez la carte suivante à jouer");	
		                    					discardCard.setVisible(false);
	                    					}
	                    					else
	                    					{
	                    						info2.setText("Appuyer sur le bouton \"Finir le tour \"");
	                    						finishTurn.setVisible(true);
	                    						discardCard.setVisible(false);
	                    					}
		                        			addToDraw(cards);	
		                        		}
	                    				else
	                    				{
	                    					info2.setText("Appuyer sur le bouton \"Finir le tour \"");
	                    					checkFinishTurn[1] = 1;
                    						finishTurn.setVisible(true);
                    						discardCard.setVisible(false);
                    						addToDraw(null);
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
    class DrawHandler extends JPanel implements ActionListener  {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            BorderOne.setBackground(new Color(0,0,0,0));
            BorderTwo.setBackground(new Color(0,0,0,0));
            BorderThree.setBackground(new Color(0,0,0,0));
            BorderFour.setBackground(new Color(0,0,0,0));
            JPanel border = (JPanel)((JButton)source).getClientProperty("border");
            border.setBackground(new Color (255,255,255));

            cardPicked = (Card)((JButton)source).getClientProperty("card");
            if(checkFinishTurn[0] == 1)
            {
            	info2.setText("Appuyer sur le bouton \"Finir le tour \"");
				finishTurn.setVisible(true);
				discardCard.setVisible(false);
            	checkFinishTurn[1] = 1;
            }
            else if(game.getRound() == 1) {
            	info2.setText("Placer votre première carte");
    			discardCard.setVisible(true);
            }
            getContentPane().repaint();
        }
    }
}



