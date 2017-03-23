package problem1;

import java.util.Set;
import java.util.TreeSet;

public class Notifier {
    private Set<Notifiable> notifiables;

    public Notifier() {
        notifiables = new TreeSet<>();
    }

    public void addNotifiable(Notifiable notifiable) {
        notifiables.add(notifiable);
    }

    public void removeNotifiable(Notifiable notifiable) {
        notifiables.remove(notifiable);
    }

    public void doNotifyAll(String message) {
        notifiables.forEach(o -> o.notify(message));
    }
}
