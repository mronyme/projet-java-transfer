

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Plateau extends JFrame implements ActionListener{

	/**
	 * 
	 */
	 private static final long serialVersionUID = 1L;
	 private JButton b1;
	 private JLabel j1, j2;
	 private Font f = new Font("Verdana", Font.PLAIN, 50);
	 private Font e = new Font("Bold", Font.PLAIN, 30);

	 private  JLabel txt1;
	 private JRadioButton br1 ,br2 ,br3;
	 private Border lineBorder = new LineBorder(Color.BLACK,2);

	 
	 public Plateau ( ) {
		 super("KingDomino");
		  setSize(1000,1000);
		  Container cp = getContentPane();
		 cp.setBackground(Color.cyan); 
		JPanel panneau1 = new JPanel();
		JPanel panneau2 = new JPanel();
		JPanel panneau21 = new JPanel();
		JPanel panneau22 = new JPanel();
		
		 cp.add(panneau1,BorderLayout.NORTH);
		 cp.add(panneau2,BorderLayout.CENTER);
		 
		// cp.add(panneau22,BorderLayout.SOUTH);
		 
		 panneau1.setPreferredSize(new Dimension(300,200));
		// panneau21.setPreferredSize(new Dimension(100,100));
		//panneau2.setPreferredSize(new Dimension(500,500));
   panneau2.add(panneau21);
  panneau2.add(panneau22);

		 panneau1.setBackground(Color.cyan); 
		 txt1=new JLabel("Bienvenue dans KingDomino ");
		 txt1.setFont(f);
		 panneau1.add(txt1);
		 
		 panneau2.setLayout(new GridLayout(3,1));
		 panneau2.setBackground(Color.cyan); 
		 j1 = new JLabel("Nombres de joueurs ");
		j1.setFont(e);
			panneau21.add(j1);
			 br1 =new JRadioButton ("2");
			 br2 =new JRadioButton ("3");
			 br3 =new JRadioButton ("4");
			panneau21.add(br1);
			panneau21.add(br2);
			panneau21.add(br3);
			

			ButtonGroup bg =new ButtonGroup();
			
			bg.add(br1);		
			bg.add(br2);	
			bg.add(br3);
			

			panneau21.setBackground(Color.cyan);
		panneau22.setBackground(Color.cyan);
		b1 =new JButton("Play");
		panneau22.add(b1);
		b1.addActionListener( this);
	    panneau2.setBorder(lineBorder);
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	 }
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int nbr_joueurs=0; 
			if(br1.isSelected()){
				
				System.out.println("nbres des joueurs est 2");
				nbr_joueurs=2; 
				
				
			}
				
			else if(br2.isSelected()){
				
				System.out.println("nbres des joueurs est 3");
				
			}
    else if(br3 .isSelected()){
				
				System.out.println("nbres des joueurs est 3");
				
			}
			
			
	
	
	

}
	//public static void main(String[] args) {
		
       //Plateau p=new Plateau();
		//p.pack();
		//p.setVisible(true);
	//	

	//}

}
