package problem4;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

public class Task1 extends JFrame {

    private JPanel root;
    private JComboBox comboBox;
    private JTextField textField;
    private JCheckBox checkBox;

    public Task1() {
        super("Pattern");

        setContentPane(root);
        checkBox.setDisabledIcon(icon(Color.RED));
        checkBox.setDisabledSelectedIcon(icon(Color.GREEN));

        checkBox.setSelected(check(textField.getText()));
        comboBox.addActionListener(e -> checkBox.setSelected(check(textField.getText())));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                checkBox.setSelected(check(textField.getText()));
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    private boolean check(String s) {
        switch (comboBox.getSelectedIndex()) {
            case 0://натуральное число
                return Pattern.matches("[1-9]\\d*", s);
            case 1://целое число
                return Pattern.matches("[-+]?(0|[1-9]\\d*)", s);
            case 2://число с плавающей запятой(так же 1e-5)
                return Pattern.matches("[-+]?(((0|[1-9]\\d*)(\\.\\d*)?|" +
                        "(\\.\\d+))" +
                        "(e([-+]?[1-9]\\d*))?)", s);
            case 3://дата
                return Pattern.matches("((0[1-9]|1\\d|2[0-8])\\.(0[1-9]|1[0-2])|" +
                        "(29|30)\\.(0[469]|11)|" +
                        "(29|3[01])\\.(0[13578]|1[02]))" +
                        "\\.(\\d{4})", s);
            case 4://время
                return Pattern.matches("([01]\\d|2[0-3])(:[0-5]\\d){2}", s);
            case 5://e-mail
                return Pattern.matches("([A-z0-9]+(\\.|_|-))*[A-z0-9]+@([A-z0-9]+\\.)+[A-z]{2,}", s);
        }
        return false;
    }

    private Icon icon(Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(color);
                g.fillOval(0,0, getIconWidth(), getIconHeight());
            }

            @Override
            public int getIconWidth() {
                return 20;
            }

            @Override
            public int getIconHeight() {
                return getIconWidth();
            }
        };
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
