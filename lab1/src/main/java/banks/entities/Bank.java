package banks.entities;

import accounts.entities.*;
import accounts.factories.CreditAccountFactory;
import accounts.factories.DebitAccountFactory;
import accounts.factories.DepositAccountFactory;
import accounts.services.DepositPercentCalculator;
import clients.entities.Client;
import clients.models.ClientAccess;
import exceptions.CrossingBalanceAmountException;
import exceptions.CrossingLowerLimitException;
import exceptions.CrossingUpperLimitException;
import exceptions.FakeClientException;
import notifications.entities.INotifier;
import notifications.entities.IObserver;
import notifications.models.Notification;
import transactions.entities.Transaction;
import transactions.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Bank implements INotifier {
    private final CentralBank centralBank;
    private long id;
    private String name;
    private double generalPercent;
    private DepositPercentCalculator depositPercentCalculator;
    private final HashMap<IAccount, Double> depositPercentsForAccounts = new HashMap<>();
    private final double commission;
    private double lowerLimit;
    private double upperLimit;
    private final ArrayList<IAccount> accounts = new ArrayList<>();
    private final ArrayList<IObserver> observers = new ArrayList<>();
    private final HashMap<IAccount, Double> remainders = new HashMap<>();
    private static long accountIdMarker = 0;
    private static long clientIdMarker = 0;
    private final List<Client> clients = new ArrayList<>();

    public Bank(CentralBank centralBank,
                String name,
                double percent,
                DepositPercentCalculator depositPercentCalculator,
                double commission,
                long lowerLimit,
                long upperLimit) {
        this.centralBank = centralBank;
        this.name = name;
        this.generalPercent = percent;
        this.depositPercentCalculator = depositPercentCalculator;
        this.commission = commission;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public void setGeneralPercent(double percent) {
        this.generalPercent = percent;

        accounts.stream()
                .filter(account -> account instanceof DebitAccount)
                .forEach(a -> setGeneralPercent(percent));
        notify(new Notification("Change of percent"));
    }


    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
        notify(new Notification ("Change of lower limit"));
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
        notify(new Notification ("Change of upper limit"));
    }

    public List<IAccount> getAccounts() {
        return List.copyOf(accounts);
    }


    public List<IObserver> getObservers() {

        return List.copyOf(observers);
    }

    @Override
    public void notify(Notification notification) {
        observers.forEach(s -> s.receiveNotification(notification));
    }

    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(IObserver observer) {
        observers.remove(observer);
    }

    public void registerClient(Client client) {
        client.setId(clientIdMarker);
        ++clientIdMarker;
        clients.add(client);
    }

    public void registerDebitAccount(long holderId, double amount) {
        IAccount account = DebitAccountFactory.createInstance(
                accountIdMarker,
                this,
                holderId);
        account.topUpBalance(amount);
        accountIdMarker++;
        accounts.add(account);
        remainders.put(account, 0.0);
    }

    public void registerCreditAccount(long holderId, double amount) {
        IAccount account = CreditAccountFactory.createInstance(
                accountIdMarker,
                this,
                holderId,
                amount);
        accountIdMarker++;
        accounts.add(account);
        remainders.put(account, 0.0);
    }

    public void registerDepositAccount(long holderId, double amount, double percent) {
        IAccount account = DepositAccountFactory.createInstance(
                accountIdMarker,
                this,
                holderId,
                amount);
        ++accountIdMarker;
        accounts.add(account);
        depositPercentsForAccounts.put(account, percent);
        remainders.put(account, 0.0);
    }

    public void closeAccount(AbstractAccount account) {
        accounts.remove(account);
    }

    public void commitTransaction(Transaction transaction) throws Exception {
        validateTransaction(transaction);
        centralBank.commitTransaction(transaction);
        transaction.getReceiverAccount().addTransactionToHistory(transaction);
        if (transaction.getType() == TransactionType.Transfer)
            transaction.getSenderAccount().addTransactionToHistory(transaction);
    }

    public void cancelTransaction(Transaction transaction) throws Exception {
        centralBank.cancelTransaction(transaction);
    }

    public void payRemains() {
        accounts.forEach(a -> a.topUpBalance(remainders.get(a)));
    }

    public void takeCommissions() {
        accounts.stream()
                .filter(account -> account instanceof CreditAccount)
                .forEach(a -> {
                    try {
                        a.reduceBalance(commission);
                    } catch (CrossingBalanceAmountException e) {
                        System.err.println("Failed to reduce balance: " + e.getMessage());
                    }
                });
    }

    public void updateRemains() {
        accounts.stream()
                .filter(account -> account instanceof DebitAccount || account instanceof DepositAccount)
                .forEach(account -> {
                    double percent = account instanceof DebitAccount ? generalPercent : depositPercentsForAccounts.get(account);
                    remainders.put(account, remainders.get(account) + account.getBalance() * percent);
                });
    }

    private void validateTransactionSubjects(Transaction transaction) throws FakeClientException {
        boolean IsSenderFound = getClients().stream()
                .anyMatch(c -> c.getId() == transaction.getSenderAccount().getHolderId());
        boolean IsReceiverFound = getClients().stream()
                .anyMatch(c -> c.getId() == transaction.getReceiverAccount().getHolderId());

        if (!IsSenderFound || !IsReceiverFound) {
            throw new FakeClientException();
        }
    }

    private void validateTransaction(Transaction transaction) throws Exception {
        double amount = transaction.getAmount();

        validateTransactionSubjects(transaction);

        Client sender = getClients().stream()
                .filter(c -> c.getId() == transaction.getSenderAccount().getHolderId())
                .findFirst()
                .orElseThrow();


        if ((sender.defineClientAccess() == ClientAccess.Restricted) && amount > upperLimit) {
            throw new CrossingUpperLimitException();
        }

        if (amount < lowerLimit) {
            throw new CrossingLowerLimitException();
        }
    }
}
