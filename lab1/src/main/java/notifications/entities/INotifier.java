package notifications.entities;

import notifications.models.Notification;

public interface INotifier {
    void notify(Notification notification);
    void subscribe(IObserver observer);
    void unsubscribe(IObserver observer);
}
