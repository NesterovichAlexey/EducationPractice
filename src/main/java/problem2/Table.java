package problem2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

public class Table extends JFrame {

    private JPanel tablePanel;
    private JTable table;
    private JButton addButton;
    private DefaultTableModel model;
    private AddTourDialog addTour = new AddTourDialog();

    private Table() {
        super("Table");

        setContentPane(tablePanel);

        model = new TableModel();
        readCountry();
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(35);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedColumn() == 0) {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(Table.this) == JFileChooser.APPROVE_OPTION) {
                        model.setValueAt(new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath()), table.getSelectedRow(), table.getSelectedColumn());
                    }
                }
            }
        });
        table.setDefaultRenderer(Boolean.class, new BooleanCellRenderer());

        addButton.addActionListener(e -> {
            addTour.setVisible(true);
            if (!addTour.isCancel()) {
                model.insertRow(model.getRowCount() - 1, new Object[]{addTour.getFlag(), addTour.getDescription(), addTour.getPrice(), false});
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
    }

    private void readCountry() {
        Scanner in = new Scanner(getClass().getResourceAsStream("/plain/countries.txt"));
        while (in.hasNext()) {
            String nameCountry = in.next();
            String capitalCountry = in.next();
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/plain/flag_" + in.next() + ".png"));
            model.insertRow(model.getRowCount() - 1, new Object[]{imageIcon, nameCountry + " " + capitalCountry, (int) (Math.random() * 10000), false});
        }
        in.close();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        new Table().setVisible(true);
    }

    class TableModel extends DefaultTableModel {
        public TableModel() {
            super(0, 4);
            addRow(new Object[]{null, "Order cost", 0, null});
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            if (column == 3) {
                if ((Boolean) aValue) {
                    setValueAt((int) getValueAt(getRowCount() - 1, 2) + (int) getValueAt(row, 2), getRowCount() - 1, 2);
                } else {
                    setValueAt((int) getValueAt(getRowCount() - 1, 2) - (int) getValueAt(row, 2), getRowCount() - 1, 2);
                }
            } else if (column == 2 && row != getRowCount() - 1) {
                setValueAt((int) getValueAt(getRowCount() - 1, 2) - (int) getValueAt(row, 2), getRowCount() - 1, 2);
                setValueAt((int) getValueAt(getRowCount() - 1, 2) + (int) aValue, getRowCount() - 1, 2);
            }
            super.setValueAt(aValue, row, column);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return ImageIcon.class;
                case 1:
                    return String.class;
                case 2:
                    return Integer.class;
                case 3:
                    return Boolean.class;
            }
            return Object.class;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Flag";
                case 1:
                    return "Description";
                case 2:
                    return "Price";
                case 3:
                    return "Selected";
            }
            return "";
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return !(column == 0) && !(row == getRowCount() - 1);
        }
    }

    public static class BooleanCellRenderer extends JCheckBox implements TableCellRenderer {
        public BooleanCellRenderer() {
            setLayout(new GridBagLayout());
            setMargin(new Insets(0, 0, 0, 0));
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (row == table.getRowCount() - 1)
                return new JLabel();
            setSelected((Boolean) value);

            return this;
        }
    }
}
