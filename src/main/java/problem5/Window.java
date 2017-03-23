package problem5;

import javax.swing.*;

public class Window extends JFrame {

    private Window() {
        super("Excel lite");

        add(new Excel());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 500);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        UIManager.put("Button[Disabled].backgroundPainter", UIManager.get("TableHeader:\"TableHeader.renderer\"[Disabled].backgroundPainter"));
        UIManager.put("Button[Enabled].backgroundPainter", UIManager.get("TableHeader:\"TableHeader.renderer\"[Enabled].backgroundPainter"));
        new Window().setVisible(true);
    }
}
