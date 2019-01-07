package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class GlassPane extends JComponent implements ItemListener {
    Point point;

    public GlassPane(AbstractButton aButton,
                     JMenuBar menuBar,
                     Container contentPane) {
        CBListener listener = new CBListener(aButton, menuBar,
                this, contentPane);
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    //React to change button clicks.
    public void itemStateChanged(ItemEvent e) {
        setVisible(e.getStateChange() == ItemEvent.SELECTED);
    }

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
