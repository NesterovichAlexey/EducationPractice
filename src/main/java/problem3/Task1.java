package problem3;

import javax.swing.*;
import java.awt.*;

public class Task1 extends JFrame {

    public Task1() {
        super("Clock");

        add(new JPanel(null) {
            private final static double D = Math.PI / 30000.;

            {
                new Timer(20, e -> repaint()).start();
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int r = Math.min(getWidth(), getHeight()) / 2 - 10;
                int x0 = getWidth() / 2;
                int y0 = getHeight() / 2;
                g.drawOval(x0 - r, y0 - r, 2 * r, 2 * r);

                long sec = System.currentTimeMillis() - 15000;
                int x = (int) (x0 + r * Math.cos(sec * D));
                int y = (int) (y0 + r * Math.sin(sec * D));
                g.drawLine(x0, y0, x, y);
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        new Task1().setVisible(true);
    }
}