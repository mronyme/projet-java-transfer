package views;

import javax.swing.*;

import core.CardManagement;
import core.CoreGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

public class IHM extends JFrame implements ActionListener {
	 JFrame fenetre;
	  JRadioButton player3 ,player2 , player4;
	private CoreGame game;
    public IHM(CoreGame game) { // Constructeur de la classe IHM
    	
         fenetre = new JFrame();
        //D�finit un titre pour notre fen�tre
this.game=game;
        this.setTitle("Domi'Nations par Arnaud, Baptiste, Chaimaa");
        // On r�cup�re la taille de l'�cran utile, pour cela on utilise l'objet ScreenSize
        ScreenSize ScreenSize = new ScreenSize();
        int width = ScreenSize.getwidth(); // R�cup�ration de la largeur
        int height = ScreenSize.getheight(); // R�cup�ration de la hauteur

        // Puis on redimensionne la JFrame en fonction
        this.setSize(width / 2, height / 2);

        //Nous demandons maintenant � notre objet de se positionner au centre

        this.setLocationRelativeTo(null);

        //Termine le processus lorsqu'on clique sur la croix rouge

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Et enfin, la rendre visible
        this.setVisible(true);

        // Composantes de la JFrame
        //Instanciation d'un objet JPanel qui peut contenir des composants ou d'autres conteneurs.
        JPanel pan = new JPanel();
        //D�finition de sa couleur de fond
        pan.setBackground(Color.ORANGE);
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
        JMenuItem restart = new JMenuItem("Recommencer la partie");
        Parametres.add(restart);


        // Bouton Play
        JButton buttonOne = new JButton(" Play !");
        buttonOne.addActionListener(this);


        // Label
        JLabel Box1 = new JLabel();
        Box1.setOpaque(true);
        Box1.setBackground(new Color(254, 255, 243, 121));
        Box1.setPreferredSize(new Dimension(this.getWidth(), 180));
        Box1.setText("Bienvenue dans Domi'Nations, choisissez le nombre de joueurs ci-dessous :");

        player2 = new JRadioButton("2 joueurs");
        player3 = new JRadioButton("3 joueurs");
        player4 = new JRadioButton("4 joueurs");
        ButtonGroup  bg = new ButtonGroup();
        bg.add(player2);
        bg.add(player3);
        bg.add(player4);

        this.add(player2);
        
        this.add(player3);
        this.add(player4); 


        //Ajout des objets pr�c�dements cr�� dans la fen�tre.
        this.setJMenuBar(MenuBar);
        this.getContentPane().add(Box1, BorderLayout.CENTER);
        this.getContentPane().add(buttonOne, BorderLayout.CENTER);


        //Display the window.


        this.pack();
        this.setVisible(true);
        game.initGame(2,false,false,false,false);

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (player2.isSelected()) {	
        System.out.println("nbres des joueurs est 2");
    	game.initGame(2,false,false,false,false);    	
    	//nouvelle fenetre pour commencer le jeu
    	final JFrame frame = new JFrame("Test");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(300, 300);
    	frame.setVisible(true);
    	
    } else if (player3.isSelected()) {
        System.out.println("nbres des joueurs est 3");
        game.initGame(3,false,false,false,false);
       
        
    } else if (player4.isSelected()) {
        System .out.println("nbres des joueurs est 4");
        game.initGame(4,false,false,false,false);
    }
	}

}
