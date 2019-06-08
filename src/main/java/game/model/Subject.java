package game.model;

import java.util.LinkedList;
import java.util.List;

public abstract class Subject {
    private List<IObserver> observers;

    public Subject() {
        this.observers = new LinkedList<>();
    }

    public void subscribe(IObserver observer) {
        if (observer != null)
            observers.add(observer);
    }

    public void unsubscribe(IObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (IObserver observer : observers) {
            if (observer == null)
                unsubscribe(null);
            else
                observer.update();
        }
    }
}
