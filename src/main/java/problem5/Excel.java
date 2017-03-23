package problem5;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DateFormat;

class Excel extends JScrollPane {

    Excel() {
        super();
        Table table = new Table();
        setViewportView(table);
        setRowHeaderView(table.getRowHeader());
    }

    class Table extends JTable {

        Table() {
            super(new ExcelModel());
            getTableHeader().setReorderingAllowed(false);
            setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            setShowGrid(true);
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setCellSelectionEnabled(true);
            setBackground(Color.WHITE);
            setDefaultRenderer(ExcelModel.Cell.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table,
                                                               Object value,
                                                               boolean isSelected,
                                                               boolean hasFocus,
                                                               int row,
                                                               int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (value != null && ((ExcelModel.Cell)value).formula != null) {
                        if (((ExcelModel.Cell) value).date == null)
                            label.setText("ERROR");
                        else
                            label.setText(DateFormat.getDateInstance().format(((ExcelModel.Cell)value).date));
                    } else
                        label.setText("");
                    return label;
                }
            });
            setDefaultEditor(ExcelModel.Cell.class, new DateEditor((ExcelModel) getModel()));
            for (int i = 0; i < getColumnModel().getColumnCount(); ++i)
                getColumnModel().getColumn(i).setPreferredWidth(100);
        }

        JList getRowHeader() {
            DefaultListModel<Object> model = new DefaultListModel<Object>() {
                @Override
                public int getSize() {
                    return getModel().getRowCount();
                }
                @Override
                public Object getElementAt(int index) {
                    return null;
                }
            };
            JList<Object> list = new JList<>(model);
            list.setFixedCellHeight(getRowHeight());
            list.setCellRenderer(new DefaultListCellRenderer() {
                JButton button = new JButton();
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    button.setText(index + 1 + "");
                    return button;
                }
            });
            list.setEnabled(false);
            return list;
        }
    }
}
