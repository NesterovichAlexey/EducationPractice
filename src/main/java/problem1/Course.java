package problem1;

import java.util.Set;
import java.util.TreeSet;

public class Course {
    private Set<Student> students;
    private String name;

    public Course(String name, Set<Student> students) {
        this.name = name;
        this.students = new TreeSet<>(students);
    }

    public Set<Postgraduate> getPostgraduates(String nameOfSupervisor) {
        TreeSet<Postgraduate> set = new TreeSet<>();
        for (Student student : students) {
            if (student instanceof Postgraduate) {
                if (((Postgraduate)student).getSupervisor() != null && nameOfSupervisor.equals(((Postgraduate)student).getSupervisor().getName())) {
                    set.add((Postgraduate) student);
                }
            }
        }
        return set;
    }
}
