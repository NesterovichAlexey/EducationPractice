package problem5;

import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static problem5.DateFromString.*;

public class DateEditor extends DefaultCellEditor {
    private ExcelModel model;
    private ExcelModel.Cell value;
    private Pair<Integer, Integer> curCell;

    DateEditor(ExcelModel model) {
        super(new JTextField());
        this.model = model;
        getComponent().setName("Table.editor");
        delegate = new EditorDelegate() {
            @Override
            public void setValue(Object value) {
                if (value == null || ((ExcelModel.Cell)value).formula == null) {
                    ((JTextField)getComponent()).setText("");
                } else
                    ((JTextField)getComponent()).setText(((ExcelModel.Cell)value).formula);
            }

            @Override
            public Object getCellEditorValue() {
                return ((JTextField)getComponent()).getText();
            }
        };
    }

    public boolean stopCellEditing() {
        String s = (String) super.getCellEditorValue();
        if (value.formula != null)
            removeDependentCells(value.formula);
        try {
            if ("".equals(s)) {
                value.set(null, null);
                return super.stopCellEditing();
            } else if (isDate(s)) {
                value.set(getDateFromString(s), s);
            } else if (isFormula1(s)) {
                value.set(getDateFromFormula1(s), s);
            } else if (isFormula2(s)) {
                Matcher matcher = Pattern.compile(CELL).matcher(s);
                if (matcher.find() && isCycle(getCellFromString(matcher.group())))
                    throw new CycleException();
                value.set(getDateFromFormula2(s, model), s);
            } else if (isMinOrMax(s)) {
                Matcher matcher = Pattern.compile(CELL).matcher(s);
                while (matcher.find()) {
                    if (isCycle(getCellFromString(matcher.group())))
                        throw new CycleException();
                }
                value.set(getMinOrMaxDate(s, model), s);
            } else
                throw new Exception("ERROR");
            value.update(model);
        } catch (Exception e) {
            if (e instanceof CycleException)
                JOptionPane.showMessageDialog(null, "Cyclic reference", "Cyclic errorr", JOptionPane.WARNING_MESSAGE);
            ((JComponent) getComponent()).setBorder(new LineBorder(Color.red));
            return false;
        } finally {
            if (value.formula != null)
                addDependentCells(value.formula);
        }
        return super.stopCellEditing();
    }

    private void addDependentCells(String s) {
        Matcher matcher = Pattern.compile(CELL).matcher(s);
        while (matcher.find()) {
            Pair<Integer, Integer> p = getCellFromString(matcher.group());
            ExcelModel.Cell cell = model.getValueAt(p.getKey(), p.getValue());
            if (cell == null) {
                cell = model.new Cell(null, null, p.getKey(), p.getValue());
                model.setValueAt(cell, p.getKey(), p.getValue());
            }
            cell.addDependentCell(value);
        }
    }

    private void removeDependentCells(String s) {
        Matcher matcher = Pattern.compile(CELL).matcher(s);
        while (matcher.find()) {
            Pair<Integer, Integer> p = getCellFromString(matcher.group());
            ExcelModel.Cell cell = model.getValueAt(p.getKey(), p.getValue());
            cell.removeDependentCell(value);
        }
    }

    private boolean isCycle(Pair<Integer, Integer> cell) {
        if (cell.equals(curCell))
            return true;
        ExcelModel.Cell a = model.getValueAt(cell.getKey(), cell.getValue());
        if (a != null && a.formula != null && isFormula2(a.formula))
            return isCycle(getCellFromString(a.formula));
        else if (a != null && a.formula != null && isMinOrMax(a.formula)) {
            Matcher matcher = Pattern.compile(CELL).matcher(a.formula);
            while (matcher.find()) {
                if (isCycle(getCellFromString(matcher.group())))
                    return true;
            }
        }
        return false;
    }

    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column) {
        this.value = (ExcelModel.Cell) value;
        if (this.value == null) {
            this.value = model.new Cell(null, null, row, column);
        }
        curCell = new Pair<>(row, column);
        ((JComponent)getComponent()).setBorder(new LineBorder(Color.black));
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    public Object getCellEditorValue() {
        return value;
    }
}

class CycleException extends Exception {
}