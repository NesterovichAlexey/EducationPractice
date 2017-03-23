package problem1;

public class Undergraduate extends Student {
    private Academic tutor;

    public Undergraduate(String login, String email) {
        super(login, email);
    }

    public Undergraduate(String name, String login, String email) {
        super(name, login, email);
    }

    public Academic getTutor() {
        return tutor;
    }

    public void setTutor(Academic tutor) {
        this.tutor = tutor;
    }
}
