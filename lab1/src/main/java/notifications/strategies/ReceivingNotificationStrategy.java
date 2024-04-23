package notifications.strategies;

import notifications.models.Notification;

public interface ReceivingNotificationStrategy {
    void react(Notification notification);
}
