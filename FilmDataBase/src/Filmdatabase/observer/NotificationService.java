package Filmdatabase.observer;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private final List<NotificationListener> listeners = new ArrayList<>();

    public void register(NotificationListener listener) {
        listeners.add(listener);
    }

    public void notifyAll(String message) {
        for (NotificationListener l : listeners) {
            l.onEvent(message);
        }
    }
}
