package views;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Panneau extends JPanel {
    public Panneau() {

    }

    public void paintComponent(Graphics g) {

        //Vous verrez cette phrase chaque fois que la méthode sera invoquée
        System.out.println("Je suis exécutée !");

        int x1 = this.getWidth() / 4;
        int y1 = this.getHeight() / 4;

        // Création d'un rectangle redimensionable
        g.fillRect(x1, y1, this.getWidth() / 2, this.getHeight() / 2);
        // Insertion du background
        try {
            Image img = ImageIO.read(new File("fond.jpg"));

            //Image de fond
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}