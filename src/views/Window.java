package views;

import core.CoreGame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Window extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JButton b1;
    private JLabel j1, j2;
    private Font f = new Font("Verdana", Font.PLAIN, 50);
    private Font e = new Font("Bold", Font.PLAIN, 30);

    private JLabel txt1;
    private JRadioButton br1, br2, br3;
    private Border lineBorder = new LineBorder(Color.BLACK, 2);
    JPanel panelCont = new JPanel();
    private CoreGame game;
    public Window(CoreGame game) {
        super("KingDomino");
    	this.game = game;
        setSize(1000, 1000);
        CardLayout cl = new CardLayout();
        panelCont.setLayout(cl);
        Container cp = new JPanel(); // à supprimer 
        cp.setBackground(Color.black);
        JPanel panneau1 = new JPanel();
        JPanel panneau2 = new JPanel();
        JPanel panneau21 = new JPanel();
        JPanel panneau22 = new JPanel();

        cp.add(panneau1, BorderLayout.NORTH);
        cp.add(panneau2, BorderLayout.CENTER);

        // cp.add(panneau22,BorderLayout.SOUTH);

        panneau1.setPreferredSize(new Dimension(300, 200));
        // panneau21.setPreferredSize(new Dimension(100,100));
        //panneau2.setPreferredSize(new Dimension(500,500));
        panneau2.add(panneau21);
        panneau2.add(panneau22);

        panneau1.setBackground(Color.cyan);
        txt1 = new JLabel("Bienvenue dans KingDomino ");
        txt1.setFont(f);
        panneau1.add(txt1);

        panneau2.setLayout(new GridLayout(3, 1));
        panneau2.setBackground(Color.cyan);
        j1 = new JLabel("Nombres de joueurs ");
        j1.setFont(e);
        panneau21.add(j1);
        br1 = new JRadioButton("2");
        br2 = new JRadioButton("3");
        br3 = new JRadioButton("4");
        panneau21.add(br1);
        panneau21.add(br2);
        panneau21.add(br3);


        ButtonGroup bg = new ButtonGroup();

        bg.add(br1);
        bg.add(br2);
        bg.add(br3);


        panneau21.setBackground(Color.cyan);
        panneau22.setBackground(Color.cyan);
        b1 = new JButton("Play");
        panneau22.add(b1);
        b1.addActionListener(this);
        panneau2.setBorder(lineBorder);
        
        panelCont.add(cp,"1");
        cl.show(panelCont, "1");
        add(panelCont);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    public void renderMenu()
    {
    	// Affiche la fenetre menu
    }
    public void renderGeneralBoard()
    {	
    	// Affiche la fenetre avec tous les territoires actualisé ( appellée à chaque début de tour de jeu) 
    }
    public void renderSelectionCard()
    {
    	// Affiche la fenetre de sï¿½lection des cartes ï¿½ l'aide des rois ( appellï¿½e ï¿½ chaque dï¿½but de tour de jeu)
    }
    public void renderPlayerBoard()
    {
    	// Affiche la fenetre avec seulement le territoire du joueur dont c'est le tour
    }
    public void renderLeaderboard()
    {
    	// Affiche la fenetre des scores de fin de partie
    }

    public void actionPerformed(ActionEvent e) {


        if (br1.isSelected()) {

            System.out.println("nbres des joueurs est 2");
            game.initGame(2);
        } else if (br2.isSelected()) {

            System.out.println("nbres des joueurs est 3");
            game.initGame(3);

        } else if (br3.isSelected()) {
            System.out.println("nbres des joueurs est 4");
            game.initGame(4);
        }


    }
}
