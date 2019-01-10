package views;

import core.CoreGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IHM extends JFrame implements ActionListener {
    ScreenSize ScreenSize = new ScreenSize();
	  JRadioButton player3 ,player2 , player4;
	private CoreGame game;
    public IHM(CoreGame game) { // Constructeur de la classe IHM
        JFrame fenetre;
         fenetre = new JFrame();
        //D?finit un titre pour notre fen?tre
        this.game = game;
        this.setTitle("Domi'Nations par Arnaud, Baptiste, Chaimaa");
        // On r?cup?re la taille de l'?cran utile, pour cela on utilise l'objet ScreenSize

        int width = ScreenSize.getwidth(); // R?cup?ration de la largeur
        int height = ScreenSize.getheight(); // R?cup?ration de la hauteur

        // Puis on redimensionne la JFrame en fonction
        setPreferredSize(new Dimension(width, height));
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
        //D?finition de sa couleur de fond
        pan.setBackground(Color.ORANGE);
        //On pr?vient notre JFrame que notre JPanel sera son content pane
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
        // Option du menu param?tres :
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


        //Ajout des objets pr?c?dements cr?? dans la fen?tre.
        this.setJMenuBar(MenuBar);
        this.getContentPane().add(Box1, BorderLayout.CENTER);
        this.getContentPane().add(buttonOne, BorderLayout.CENTER);


        //Display the window.



        this.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (player2.isSelected()) {	
        System.out.println("nbres des joueurs est 2");
            //nouvelle fenetre pour commencer le jeu
            this.setTitle("Domi'Nations pour 2 joueurs");
            this.setSize(ScreenSize.getwidth(), ScreenSize.getheight());
            this.setLocationRelativeTo(null);

            //On d�finit le layout � utiliser sur le content pane

            this.setLayout(new BorderLayout());

            //On ajoute le bouton au content pane de la JFrame

            //Au centre

            this.getContentPane().add(new JButton("CENTER"), BorderLayout.CENTER);

            //Au nord

            this.getContentPane().add(new JButton("NORTH"), BorderLayout.NORTH);

            //Au sud

            this.getContentPane().add(new JButton("SOUTH"), BorderLayout.SOUTH);

            //� l'ouest

            this.getContentPane().add(new JButton("WEST"), BorderLayout.WEST);

            //� l'est

            this.getContentPane().add(new JButton("EAST"), BorderLayout.EAST);


            this.setVisible(true);
    	game.initGame(2,false,false,false,false);    	

    	
    } else if (player3.isSelected()) {
        System.out.println("nbres des joueurs est 3");
            final JFrame frame = new JFrame("Domi'Nations pour 3 joueurs");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(ScreenSize.getwidth(), ScreenSize.getheight());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            game.initGame(3, false, false, false, false);

    } else if (player4.isSelected()) {
        System .out.println("nbres des joueurs est 4");
            final JFrame frame = new JFrame("Domi'Nations pour 4 joueurs");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(ScreenSize.getwidth(), ScreenSize.getheight());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            game.initGame(4, false, false, false, false);
    }
	}

}
