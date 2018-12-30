package views;

import core.CoreGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window2 {
    JButton buttonOne = new JButton(" Play !");
    JFrame frame = new JFrame("CardLayout demo");
    JPanel panelCont = new JPanel();
    JPanel panelFirst = new JPanel();
    JPanel panelSecond = new JPanel();
    JButton buttonSecond = new JButton(" Revenir au menu");
    private Font f = new Font("Verdana", Font.PLAIN, 50);
    private ButtonGroup bg;
    CardLayout cl = new CardLayout();
    private JLabel txt1;
    private JRadioButton br1, br2, br3;
    private CoreGame game;

    public Window2(CoreGame game) {
        this.game = game;
        panelCont.setLayout(cl);

        panelFirst.add(buttonOne);
        panelSecond.add(buttonSecond);
        panelFirst.setBackground(Color.WHITE);
        txt1 = new JLabel("Bienvenue dans KingDomino ");
        txt1.setFont(f);

        br1 = new JRadioButton("2 joueurs");
        br2 = new JRadioButton("3 joueurs");
        br3 = new JRadioButton("4 joueurs");
        panelFirst.add(txt1);
        panelFirst.add(br1);
        panelFirst.add(br2);
        panelFirst.add(br3);
        bg = new ButtonGroup();
        bg.add(br1);
        bg.add(br2);
        bg.add(br3);



        panelSecond.setBackground(Color.BLACK);

        panelCont.add(panelFirst, "1");
        panelCont.add(panelSecond, "2");
        cl.show(panelCont, "1");

        buttonOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cl.show(panelCont, "2");
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
        });

        buttonSecond.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cl.show(panelCont, "1");

            }
        });

        frame.add(panelCont);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


}
