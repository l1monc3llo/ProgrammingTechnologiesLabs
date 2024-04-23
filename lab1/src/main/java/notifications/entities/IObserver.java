package notifications.entities;

import notifications.models.Notification;

public interface IObserver {
    void receiveNotification(Notification message);
}
