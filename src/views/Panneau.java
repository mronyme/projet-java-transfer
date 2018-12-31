package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Panneau extends JPanel {


    public void paintComponent(Graphics g) {

        //Vous verrez cette phrase chaque fois que la méthode sera invoquée
        System.out.println("Je suis exécutée !");

        int x1 = this.getWidth() / 4;
        int y1 = this.getHeight() / 4;
        // On créer un objet Font
        Font font = new Font("IMPACT", Font.PLAIN, 20);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("Bienvenue sur Domi'Nations", this.getWidth() / 3, this.getHeight() / 10);
        g.fillRect(x1, y1, this.getWidth() / 2, this.getHeight() / 2);
        try {
            Image img = ImageIO.read(new File("fond.jpg"));

            //Image de fond
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}