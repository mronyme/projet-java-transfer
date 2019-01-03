package views;

import javax.swing.*;
import java.awt.*;

public class IHM extends JFrame {


    public IHM() { // Constructeur de la classe IHM

        JFrame fenetre = new JFrame();
        //Définit un titre pour notre fenêtre

        this.setTitle("Domi'Nations par Arnaud, Baptiste, Chaimaa");
        // On récupère la taille de l'écran utile, pour cela on utilise l'objet ScreenSize
        ScreenSize ScreenSize = new ScreenSize();
        int width = ScreenSize.getwidth(); // Récupération de la largeur
        int height = ScreenSize.getheight(); // Récupération de la hauteur

        // Puis on redimensionne la JFrame en fonction
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
        //Définition de sa couleur de fond
        pan.setBackground(Color.ORANGE);
        //On prévient notre JFrame que notre JPanel sera son content pane
        this.setContentPane(new Panneau());
        // Menu Bar
        JMenuBar MenuBar = new JMenuBar();
        MenuBar.setOpaque(true);
        MenuBar.setBackground(new Color(255, 255, 255));
        MenuBar.setPreferredSize(new Dimension(this.getWidth(), 20));
        JMenu Accueil = new JMenu("Accueil");
        String str1 = "Paramètres";

        JMenu Parametres = new JMenu(str1);
        JMenu Aide = new JMenu("Aide");

        MenuBar.add(Accueil);
        MenuBar.add(Parametres);
        MenuBar.add(Aide);

        JMenuItem restart = new JMenuItem("Recommencer la partie");
        Parametres.add(restart);


        // Bouton Play
        JButton buttonOne = new JButton(" Play !");


        // Label
        JLabel Box1 = new JLabel();
        Box1.setOpaque(true);
        Box1.setBackground(new Color(254, 255, 243, 121));
        Box1.setPreferredSize(new Dimension(this.getWidth(), 180));
        Box1.setText("Bienvenue dans Domi'Nations, choisissez le nombre de joueurs ci-dessous :");

        JRadioButton player2 = new JRadioButton("player2");
        JRadioButton player3 = new JRadioButton("player3");
        JRadioButton player4 = new JRadioButton("player4");
        ButtonGroup  bg = new ButtonGroup();
        bg.add(player2);
        bg.add(player3);
        bg.add(player4);

        this.add(player2);
        this.add(player3);
        this.add(player4);


        //Ajout des objets précédements créé dans la fenêtre.
        this.setJMenuBar(MenuBar);
        this.getContentPane().add(Box1, BorderLayout.CENTER);
        this.getContentPane().add(buttonOne, BorderLayout.CENTER);


        //Display the window.


        this.pack();
        this.setVisible(true);




    }

}
