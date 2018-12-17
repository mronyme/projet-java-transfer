package views;

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

    public Window2() {
        panelCont.setLayout(cl);

        panelFirst.add(buttonOne);
        panelSecond.add(buttonSecond);
        panelFirst.setBackground(Color.WHITE);
        txt1 = new JLabel("Bienvenue dans KingDomino ");
        txt1.setFont(f);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window2();
            }
        });
    }
}
