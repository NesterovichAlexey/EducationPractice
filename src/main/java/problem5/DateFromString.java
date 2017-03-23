package problem5;

import javafx.util.Pair;

import javax.swing.table.TableModel;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class DateFromString {
    static final String CELL = "(([A-z]+)([0-9]+))";
    static final String DATE = "((((0[1-9]|[12][0-8]|19)[.](0[1-9]|1[0-2]))|((29|30)[.](0[13-9]|1[0-2]))|((31)[.](0[13578]|1[02])))[.]([0-9]{4}))";
    static final String FORMULA1 = "(=" + DATE + "([+-][0-9]+)?)";
    static final String FORMULA2 = "(=" + CELL + "([+-][0-9]+)?)";
    static final String MIN_OR_MAX = "(=(min|MIN|мин|МИН|max|MAX|макс|МАКС)[(]((" + DATE + "|" + CELL + "),)*(" + DATE + "|" + CELL + ")[)])";
    static final int MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

    static Pair<Integer, Integer> getCellFromString(String s) {
        Matcher matcher = Pattern.compile(CELL).matcher(s);
        matcher.find();
        String sx = matcher.group(2).toUpperCase();
        int x = 0;
        for (int i = 0; i < sx.length(); ++i)
            x = x * 26 + (sx.charAt(i) - 'A' + 1);
        int y = Integer.parseInt(matcher.group(3));
        return new Pair<>(y - 1, x - 1);
    }

    static Date getDateFromCell(Pair<Integer, Integer> cell, TableModel model) {
        ExcelModel.Cell c = (ExcelModel.Cell) model.getValueAt(cell.getKey(), cell.getValue());
        if (c == null)
            return null;
        return c.date;
    }

    static Date getDateFromString(String s) {
        Matcher matcher = Pattern.compile(DATE).matcher(s);
        matcher.find();
        int day, month;
        if (matcher.group(3) != null) {
            day = Integer.parseInt(matcher.group(4));
            month = Integer.parseInt(matcher.group(5));
        } else if (matcher.group(6) != null) {
            day = Integer.parseInt(matcher.group(7));
            month = Integer.parseInt(matcher.group(8));
        } else {
            day = Integer.parseInt(matcher.group(10));
            month = Integer.parseInt(matcher.group(11));
        }
        int year = Integer.parseInt(matcher.group(12));
        return new GregorianCalendar(year, month - 1, day).getTime();
    }

    static Date getDateFromFormula1(String s) {
        Matcher matcher = Pattern.compile(FORMULA1).matcher(s);
        matcher.find();
        Date date = getDateFromString(matcher.group(2));
        long add = 0;
        if (matcher.group(14) != null)
            add = Integer.parseInt(matcher.group(14));
        return new Date(date.getTime() + add * MILLISECONDS_PER_DAY);
    }

    static Date getDateFromFormula2(String s, TableModel model) throws CycleException {
        Matcher matcher = Pattern.compile(FORMULA2).matcher(s);
        matcher.find();
        Date date = getDateFromCell(getCellFromString(matcher.group(2)), model);
        if (date == null)
            return null;
        long add = 0;
        if (matcher.group(5) != null)
            add = Integer.parseInt(matcher.group(5));
        return new Date(date.getTime() + add * MILLISECONDS_PER_DAY);
    }

    static Date getMinOrMaxDate(String s, TableModel model) {
        boolean isMin = s.substring(1, 4).matches("min|MIN|мин|МИН");
        Matcher matcher = Pattern.compile(DATE + "|" + CELL).matcher(s);
        Date ansDate = null;
        while (matcher.find()) {
            Date curDate;
            if (matcher.group(1) != null) {
                curDate = getDateFromString(matcher.group(1));
            } else
                curDate = getDateFromCell(getCellFromString(matcher.group(13)), model);
            if (curDate == null)
                return null;
            if (ansDate == null) {
                ansDate = curDate;
            } else if (isMin && ansDate.after(curDate)) {
                ansDate = curDate;
            } else if (!isMin && ansDate.before(curDate))
                ansDate = curDate;
        }
        return ansDate;
    }

    static boolean isCell(String s) {
        return Pattern.matches(CELL, s);
    }

    static boolean isDate(String s) {
        return Pattern.matches(DATE, s);
    }

    static boolean isFormula1(String s) {
        return Pattern.matches(FORMULA1, s);
    }

    static boolean isFormula2(String s) {
        return Pattern.matches(FORMULA2, s);
    }

    static boolean isMinOrMax(String s) {
        return Pattern.matches(MIN_OR_MAX, s);
    }
}
