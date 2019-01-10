package views;

import core.CoreGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IHM extends JFrame implements ActionListener {
    ScreenSize ScreenSize = new ScreenSize();
    JRadioButton player3, player2, player4;
    JLabel Box1 = new JLabel();
    JButton buttonOne = new JButton(" Play !");

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
        //Nous demandons maintenant à notre objet de se positionner au centre

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
        JMenuItem fullscreen = new JMenuItem("Plein écran");
        fullscreen.addActionListener(new fullscreen());
        Parametres.add(fullscreen);

        // Bouton Play
        buttonOne.addActionListener(this);

        // Label
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

    public void Plateau(int nb_joueurs) {
        switch (nb_joueurs) {

            case 2:
                System.out.println("nbres des joueurs est 2");
                //nouveau plateau pour commencer le jeu
                this.setTitle("Domi'Nations pour " + nb_joueurs + " joueurs");
                this.setSize(ScreenSize.getwidth(), ScreenSize.getheight());
                this.setLocationRelativeTo(null);
                this.remove(Box1);
                this.remove(buttonOne);
                this.remove(player2);
                this.remove(player3);
                this.remove(player4);
                //On définit le layout à utiliser sur le content pane
                this.setLayout(new BorderLayout());

                //On ajoute le bouton au content pane de la JFrame

                //Au nord
                this.getContentPane().add(new JButton("0"), BorderLayout.NORTH);
                // Ajout de la grille
                this.setLayout(new GridLayout(5, 5));
                //On ajoute le bouton au content pane de la JFrame

                for (int i = 1; i < 25; i++) {

                    this.getContentPane().add(new JButton("zone " + i + ""));

                }


                this.setVisible(true);

                break;

            case 3:

                this.setTitle("Domi'Nations pour " + nb_joueurs + " joueurs");
                this.setSize(ScreenSize.getwidth(), ScreenSize.getheight());
                this.setLocationRelativeTo(null);
                this.remove(Box1);
                this.remove(buttonOne);
                this.remove(player2);
                this.remove(player3);
                this.remove(player4);
                this.setLayout(new BorderLayout());
                this.getContentPane().add(new JButton("0"), BorderLayout.NORTH);
                // Ajout de la grille
                this.setLayout(new GridLayout(5, 5));
                //On ajoute le bouton au content pane de la JFrame

                for (int i = 1; i < 25; i++) {

                    this.getContentPane().add(new JButton("zone " + i + ""));

                }
                this.setVisible(true);

                break;

            case 4:

                this.setTitle("Domi'Nations pour " + nb_joueurs + " joueurs");
                this.setSize(ScreenSize.getwidth(), ScreenSize.getheight());
                this.setLocationRelativeTo(null);
                this.remove(Box1);
                this.remove(buttonOne);
                this.remove(player2);
                this.remove(player3);
                this.remove(player4);
                this.setLayout(new BorderLayout());
                this.getContentPane().add(new JButton("0"), BorderLayout.NORTH);
                // Ajout de la grille
                this.setLayout(new GridLayout(5, 5));
                //On ajoute le bouton au content pane de la JFrame

                for (int i = 1; i < 25; i++) {

                    this.getContentPane().add(new JButton("zone " + i + ""));

                }
                this.setVisible(true);

                break;

            default:

                System.out.println("Erreur dans la sélection du nombre de jouers");

        }

    }
    @Override

    public void actionPerformed(ActionEvent e) {

        if (player2.isSelected()) {
            Plateau(2);
            game.initGame(2, false, false, false, false);


        } else if (player3.isSelected()) {
            Plateau(3);

            game.initGame(3, false, false, false, false);

        } else if (player4.isSelected()) {
            Plateau(4);

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
}
