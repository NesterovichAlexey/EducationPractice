package problem3;

import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Task3 extends JFrame {
    private ArrayList<Pair<Integer, String>> data;
    private int sumOfValue;

    public Task3() {
        super("Image");

        readData();

        add(new JPanel(null) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                int r = Math.min(getWidth(), getHeight()) / 2 - 10;
                int x0 = getWidth() / 2;
                int y0 = getHeight() / 2;

                double angle = 0;
                double d = 360. / sumOfValue;
                int dColor = (int)(256. * 256 * 256 / data.size());
                int color = 0;
                int y = 0;
                for (Pair<Integer, String> e : data) {
                    g.setColor(new Color(color));
                    g.fillArc(x0 - r, y0 - r, 2 * r, 2 * r, (int) Math.round(angle), (int) Math.round(e.getKey() * d) + 1);
                    angle += e.getKey() * d;
                    color += dColor;
                }
                color = 0;
                for (Pair<Integer, String> e : data) {
                    g.setColor(new Color(color));
                    g.fillRect(5, 5 + y, 20, 20);
                    g.setColor(Color.BLACK);
                    g.drawString(e.getValue(), 30, 20 + y);
                    color += dColor;
                    y += 25;
                }
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(new Dimension(140, 140));
    }

    private void readData() {
        data = new ArrayList<>();
        sumOfValue = 0;
        Scanner in = new Scanner(getClass().getResourceAsStream("/diagram.txt"));
        try {
            while (in.hasNext()) {
                data.add(new Pair<>(in.nextInt(), in.nextLine()));
                sumOfValue += data.get(data.size() - 1).getKey();
                if (data.get(data.size() - 1).getKey() < 0)
                    throw new NegativeMeaningException();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to read file or incorrect data", "Error", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        in.close();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        new Task3().setVisible(true);
    }
}

class NegativeMeaningException extends Exception {
}