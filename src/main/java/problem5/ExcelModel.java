package problem5;

import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;

import static problem5.DateFromString.*;

public class ExcelModel extends DefaultTableModel {

    ExcelModel() {
        super(100, 100);
        setValueAt(new Cell(new Date(), DateFormat.getDateInstance().format(new Date()), 0, 0), 0, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Cell.class;
    }

    @Override
    public Cell getValueAt(int row, int column) {
        return (Cell) super.getValueAt(row, column);
    }

    class Cell {
        private HashSet<Cell> dependentCells = new HashSet<>();
        private int x, y;
        Date date;
        String formula;

        Cell(Date date, String formula, int x, int y) {
            this.date = date;
            this.formula = formula;
            this.x = x;
            this.y = y;
        }

        void set(Date date, String formula) {
            this.date = date;
            this.formula = formula;
        }

        void addDependentCell(Cell cell) {
            dependentCells.add(cell);
        }

        void removeDependentCell(Cell cell) {
            dependentCells.remove(cell);
        }

        void update(ExcelModel model) {
            if (formula != null && isFormula2(formula)) {
                try {
                    date = getDateFromFormula2(formula, model);
                } catch (CycleException ignored) {
                }
            } else if (formula != null && isMinOrMax(formula)) {
                date = getMinOrMaxDate(formula, model);
            }
            model.fireTableCellUpdated(x, y);
            dependentCells.forEach(o -> o.update(model));
        }
    }
}