package problem1;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class ProgrammingTest {
    private static String[][] s = new String[][]{{"u1", "uu11", "u1@mail.ru", ""},
            {"u2", "uu22", "u2@mail.ru", ""},
            {"p1", "pp11", "p1@mail.ru", "p"},
            {"p2", "pp22", "p2@mail.ru", "d"},
            {"p3", "pp33", "p3@mail.ru", "p"}};

    public static void main(String[] args) {
        TreeSet<Student> students = new TreeSet<>();
        Undergraduate undergraduate = new Undergraduate(s[0][0], s[0][1], s[0][2]);
        undergraduate.setTutor(new Academic(s[0][3]));
        students.add(undergraduate);
        undergraduate = new Undergraduate(s[1][0], s[1][1], s[1][2]);
        undergraduate.setTutor(new Academic(s[1][3]));
        students.add(undergraduate);
        for (int i = 2; i < 5; i++) {
            Postgraduate postgraduate = new Postgraduate(s[i][0], s[i][1], s[i][2]);
            postgraduate.setSupervisor(new Academic(s[i][3]));
            students.add(postgraduate);
        }
        Course course = new Course("2", students);

        Scanner in = new Scanner(System.in);
        Set<Postgraduate> set = course.getPostgraduates(in.nextLine());

        Notifier notifier = new Notifier();
        set.forEach(notifier::addNotifiable);
        notifier.doNotifyAll(in.nextLine());
    }
}
