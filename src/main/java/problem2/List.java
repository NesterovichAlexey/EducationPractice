package problem2;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.TreeMap;

public class List extends JFrame {

    private JPanel listPanel;
    private JList<String> list;
    private JLabel label;

    private TreeMap<String, Pair<String, ImageIcon>> country;

    private List() {
        super("List");

        readCountry();
        DefaultListModel<String> model = new DefaultListModel<>();
        country.forEach((o1, o2) -> model.addElement(o1));
        list.setModel(model);

        setContentPane(listPanel);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setIcon(country.get(value.toString()).getValue());
                return this;
            }
        });
        list.addListSelectionListener(o -> {
            String nameCountry = list.getSelectedValue();
            label.setIcon(country.get(nameCountry).getValue());
            label.setText("<html>Страна: " + nameCountry + "<br>Cтолица: " + country.get(nameCountry).getKey());
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
    }

    private void readCountry() {
        country = new TreeMap<>();
        Scanner in = new Scanner(getClass().getResourceAsStream("/plain/countries.txt"));
        while (in.hasNext()) {
            String nameCountry = in.next();
            country.put(nameCountry, new Pair<>(in.next(), new ImageIcon(getClass().getResource("/plain/flag_" + in.next() + ".png"))));
        }
        in.close();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        new List().setVisible(true);
    }

}
