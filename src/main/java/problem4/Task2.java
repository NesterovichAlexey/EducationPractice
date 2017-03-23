package problem4;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 extends JFrame {
    private JPanel root;
    private JList<String> list;
    private JTextArea textArea;

    public Task2() {
        super("Data");

        setContentPane(root);
        DefaultListModel<String> model = new DefaultListModel<>();
        list.setModel(model);
        textArea.addCaretListener(e -> {
            model.clear();
            Matcher matcher = Pattern.compile("((0[1-9]|1\\d|2[0-8])\\.(0[1-9]|1[0-2])|(29|30)\\.(0[469]|11)|(29|3[01])\\.(0[13578]|1[02]))\\.(\\d{4})").matcher(textArea.getText());
            while (matcher.find()) {
                model.addElement(matcher.group());
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
        new Task2().setVisible(true);
    }
}
