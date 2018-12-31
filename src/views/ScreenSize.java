package views;

import java.awt.*;

public class ScreenSize {

    //Variables d'instance
    private int width;
    private int height;

    // Constructeur
    public ScreenSize() {
        width = this.getwidth();
        height = this.getheight();


    }

    // Accesseurs
    public int getheight() {

        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height;
        height = (int) dimension.getHeight();
        return height;
    }

    public int getwidth() {

        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width;
        width = (int) dimension.getWidth();
        return width;
    }
}
