package views;

import javax.swing.*;
import java.awt.*;

public class crashtest extends JFrame {

    public crashtest() { // Constructeur de la classe crashtest
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

        // Le Content Pane :

        //Instanciation d'un objet JPanel qui peut contenir des composants ou d'autres conteneurs.
        JPanel pan = new JPanel();
        //Définition de sa couleur de fond
        pan.setBackground(Color.ORANGE);
        //On prévient notre JFrame que notre JPanel sera son content pane
        this.setContentPane(new Panneau());
        this.setVisible(true);
        // Barre de menu

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Accueil");
        pan.add(menu);




    }

}
