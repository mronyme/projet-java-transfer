package views;

import core.CoreGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IHM extends JFrame implements ActionListener {
    JPanel maincontainer = new JPanel();
    ScreenSize ScreenSize = new ScreenSize();
    JRadioButton player3, player2, player4;
    String text = "Bienvenue dans Domi'Nations, choisissez le nombre de joueurs ci-dessous :";
    JLabel Box1 = new JLabel(" " + text + " ");
    JButton buttonOne = new JButton(" Play !");
    JPanel secondcontainer = new JPanel();
    private JLayeredPane layeredPane;

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
        /*Border border = maincontainer.getBorder();
        Border margin = new EmptyBorder(0,50,0,50);
        maincontainer.setBorder(new CompoundBorder(border,margin));
     */


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
        JMenuItem fullscreen = new JMenuItem("Plein écran");
        fullscreen.addActionListener(new fullscreen());
        Parametres.add(fullscreen);

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
        ButtonGroup  bg = new ButtonGroup();
        bg.add(player2);
        bg.add(player3);
        bg.add(player4);


        //Ajout des objets précédements créé dans la fen?tre.
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

    public void aside(int nb_joueurs) {

        secondcontainer.setBackground(Color.ORANGE);
        secondcontainer.setPreferredSize(new Dimension(500, 500));
        System.out.println("height" + secondcontainer.getPreferredSize().height + " width" + secondcontainer.getPreferredSize().width);
        this.getContentPane().add(secondcontainer, BorderLayout.EAST);

    }
    // ---------------------------------------------------------------------------------------------------------------------
    public void Plateau(int nb_joueurs) {

        switch (nb_joueurs) {

            case 2:
                System.out.println("nbres des joueurs est 2");
                //nouveau plateau pour commencer le jeu
                this.setTitle("Domi'Nations pour " + nb_joueurs + " joueurs");

                this.setLocationRelativeTo(null);
                maincontainer.remove(Box1);
                maincontainer.remove(buttonOne);
                maincontainer.remove(player2);
                maincontainer.remove(player3);
                maincontainer.remove(player4);
                maincontainer.setPreferredSize(new Dimension(this.getWidth() / 2, this.getHeight() - 100));
                //On définit le layout à utiliser sur le content pane

                //On ajoute le bouton au content pane de la JFrame

                //Au nord
                maincontainer.add(new JButton("0"), BorderLayout.WEST);
                // Ajout de la grille
                maincontainer.setLayout(new GridLayout(9, 9));
                //On ajoute le bouton au content pane de la JFrame
                for (int i = 1; i < 81; i++) {

                    maincontainer.add(new JButton("zone " + i + ""));

                }

                aside(4);
                this.setVisible(true);

                break;

            case 3:

                this.setTitle("Domi'Nations pour " + nb_joueurs + " joueurs");

                this.setLocationRelativeTo(null);
                maincontainer.remove(Box1);
                maincontainer.remove(buttonOne);
                maincontainer.remove(player2);
                maincontainer.remove(player3);
                maincontainer.remove(player4);
                maincontainer.setSize(this.getWidth() / 2, this.getHeight() - 100);
                //On définit le layout à utiliser sur le content pane
                this.setLayout(new BorderLayout());
                //On ajoute le bouton au content pane de la JFrame

                //Au nord
                maincontainer.add(new JButton("0"), BorderLayout.WEST);
                // Ajout de la grille
                maincontainer.setLayout(new GridLayout(9, 9));
                //On ajoute le bouton au content pane de la JFrame
                for (int i = 1; i < 81; i++) {

                    maincontainer.add(new JButton("zone " + i + ""));

                }


                this.setVisible(true);


                break;

            case 4:

                this.setTitle("Domi'Nations pour " + nb_joueurs + " joueurs");

                this.setLocationRelativeTo(null);
                maincontainer.remove(Box1);
                maincontainer.remove(buttonOne);
                maincontainer.remove(player2);
                maincontainer.remove(player3);
                maincontainer.remove(player4);
                maincontainer.setSize(this.getWidth() / 2, this.getHeight() - 100);
                //On définit le layout à utiliser sur le content pane
                this.setLayout(new BorderLayout());
                //On ajoute le bouton au content pane de la JFrame

                //Au nord
                maincontainer.add(new JButton("0"), BorderLayout.WEST);
                // Ajout de la grille
                maincontainer.setLayout(new GridLayout(9, 9));
                //On ajoute le bouton au content pane de la JFrame
                for (int i = 1; i < 81; i++) {

                    maincontainer.add(new JButton("zone " + i + ""));

                }


                this.setVisible(true);

                break;

            default:

                System.out.println("Erreur dans la sélection du nombre de jouers");

        }

    }


    // ---------------------------------------------------------------------------------------------------------------------
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
