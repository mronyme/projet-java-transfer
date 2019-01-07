package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class GlassPane extends JComponent implements ItemListener {
    Point point;

    public GlassPane(AbstractButton aButton,
                     JMenuBar menuBar, JRadioButton player2, JRadioButton player3, JRadioButton player4, Container contentPane) {
        CBListener listener = new CBListener(aButton, menuBar, player2, player3, player4,
                this, contentPane);
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    //React to change button clicks.
    public void itemStateChanged(ItemEvent e) {
        setVisible(e.getStateChange() == ItemEvent.SELECTED);
    }
    // React to player2 button clicks.


    protected void paintComponent(Graphics g) {
        if (point != null) {
            g.setColor(Color.red);
            g.fillOval(point.x - 10, point.y - 10, 20, 20);
        }
    }

    public void setPoint(Point p) {
        point = p;
    }
}
