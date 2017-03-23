package problem3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Task2 extends JFrame {
    private BufferedImage image;
    private JSlider speedSlider;
    private JComboBox<String> drivingDirectionComboBox;

    public Task2() {
        super("Image");
        setLayout(new BorderLayout());

        try {
            image = ImageIO.read(getClass().getResource("/students/world.png"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not upload image.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        JPanel settingPanel = new JPanel();
            settingPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.PAGE_AXIS));
            speedSlider = new JSlider(1, 1000, 50);
            settingPanel.add(speedSlider);
            drivingDirectionComboBox = new JComboBox<>(new String[]{"Clockwise", "Counter-clockwise"});
                drivingDirectionComboBox.setSelectedIndex(0);
            settingPanel.add(drivingDirectionComboBox);
        add(settingPanel, BorderLayout.NORTH);

        add(new JPanel(null) {
            private double angle = 0;

            {
                new Timer(10, e -> repaint()).start();
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                int r = Math.min(getWidth(), getHeight()) / 2 - 10;
                int x0 = getWidth() / 2;
                int y0 = getHeight() / 2;
                g.drawOval(x0 - r, y0 - r, 2 * r, 2 * r);

                int direction = (drivingDirectionComboBox.getSelectedIndex() == 0 ? 1 : -1);
                double speed = speedSlider.getValue() * 0.1;
                angle += direction * (1. * speed / r);
                int x = (int) (x0 + r * Math.cos(angle) - image.getWidth() / 2);
                int y = (int) (y0 + r * Math.sin(angle) - image.getHeight() / 2);
                g.drawImage(image, x, y, null);
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(new Dimension(140, 140));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        new Task2().setVisible(true);
    }
}