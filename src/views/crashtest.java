package views;

import core.CoreGame;

import javax.swing.*;
import java.awt.*;

public class crashtest extends JFrame {
    public crashtest(CoreGame game) {
        JFrame fenetre = new JFrame();
        //Définit un titre pour notre fenêtre

        this.setTitle("Domi'Nations par Arnaud, Baptiste, Chaimaa");
        int width = width();
        int height = height();

        this.setSize(width / 2, height / 2);

        //Nous demandons maintenant à notre objet de se positionner au centre

        this.setLocationRelativeTo(null);

        //Termine le processus lorsqu'on clique sur la croix rouge

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Et enfin, la rendre visible
        this.setVisible(true);

        // Le Content Pane :
        //Instanciation d'un objet JPanel
        JPanel pan = new JPanel();
        //Définition de sa couleur de fond
        pan.setBackground(Color.ORANGE);
        //On prévient notre JFrame que notre JPanel sera son content pane
        this.setContentPane(new Panneau());
        this.setVisible(true);


    }

    public int height() {

        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height;
        height = (int) dimension.getHeight();
        return height;
    }

    public int width() {

        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width;
        width = (int) dimension.getWidth();
        return width;
    }

}
