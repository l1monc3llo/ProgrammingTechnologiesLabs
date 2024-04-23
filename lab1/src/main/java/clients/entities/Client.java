package clients.entities;
import clients.models.ClientAccess;
import clients.models.ClientPassportData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import notifications.entities.IObserver;
import notifications.models.Notification;
import notifications.strategies.ReceivingNotificationStrategy;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class Client implements IObserver {
    private long id;
    private String firstName;
    private String lastName;
    @Builder.Default
    private String address = "";
    @Builder.Default
    private ClientPassportData passportData = null;
    private final ArrayList<ReceivingNotificationStrategy> strategies = new ArrayList<>();
    private ClientAccess clientAccess;

    public List<ReceivingNotificationStrategy> getStrategies() {
        return List.copyOf(strategies);
    }

    public void addStrategy(ReceivingNotificationStrategy strategy) {
        strategies.add(strategy);
    }

    public void removeStrategy(ReceivingNotificationStrategy strategy) {
        strategies.remove(strategy);
    }

    @Override
    public void receiveNotification(Notification notification) {
        strategies.forEach(s -> s.react(notification));
    }

    public ClientAccess defineClientAccess(){
        if (address.isEmpty() || passportData == null){
            return ClientAccess.Restricted;
        }
        return ClientAccess.Full;
    }
}
