package views;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import core.*;

import java.awt.Color;
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
	    private Font f = new Font("Verdana", Font.PLAIN, 30);
	private ButtonGroup bg;
	    private JLabel txt1;
	    private JRadioButton br1, br2, br3;
	    private Border lineBorder = new LineBorder(Color.BLACK, 2);
	    private JPanel panneau21, panneau22,panneau23;
	    JPanel panelCont = new JPanel();
	    private static CoreGame game;
	    
    public Window(CoreGame game) {

        super("KingDominooo");
    	this.game = game;
    
      
   	 
   		Container contenu = this.getContentPane();
   		setSize(400, 300);
   		contenu.setLayout(new BorderLayout());
   		
   		
   		
   		 panneau21 = new JPanel();
   		 panneau22 = new JPanel();
   		 panneau23 = new JPanel();
   		
   		
  
   		panneau21.setLayout(new FlowLayout());
   		
   		contenu.setLayout(new BorderLayout());
   		contenu.add(panneau21,BorderLayout.NORTH);
   		contenu.add(panneau22,BorderLayout.CENTER);
   		contenu.add(panneau23,BorderLayout.SOUTH);
   		   
   	    panneau22.setLayout(new GridLayout(3,1));
   	 
   	    txt1=new JLabel("Nombre de joueur");
   		txt1.setFont(f);
   		panneau21.add(txt1);
   		
   		 br1 =new JRadioButton ("2 joueurs");
   		 br2 =new JRadioButton ("3 joueurs");
   		 br3 =new JRadioButton ("4 joueurs");
   		panneau22.add(br1);
   		panneau22.add(br2);
   		panneau22.add(br3);
   		 bg =new ButtonGroup();
   		
   		bg.add(br1);		
   		bg.add(br2);	
   		bg.add(br3);	
   	
   		panneau23.setLayout(new FlowLayout());
   		 b1 =new JButton("Play");
   	
   		panneau23.add(b1);
       
   	 b1.addActionListener(this);
        panneau22.setBorder(lineBorder);
        
      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void renderMenu()
    {
    	// Affiche la fenetre menu
    }
    public void renderGeneralBoard()
    {	
    	// Affiche la fenetre avec tous les territoires actualis� ( appell�e � chaque d�but de tour de jeu) 
    }
    public void renderSelectionCard()
    {
    	// Affiche la fenetre de s�lection des cartes � l'aide des rois ( appell�e � chaque d�but de tour de jeu)
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
