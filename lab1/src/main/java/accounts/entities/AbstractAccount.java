package accounts.entities;
import banks.entities.Bank;
import exceptions.CrossingBalanceAmountException;
import lombok.*;
import transactions.entities.Transaction;
import transactions.factories.DepositTransactionFactory;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public abstract class AbstractAccount implements IAccount {
    private final long id;
    private final Bank bank;
    private final long holderId;
    @Setter(AccessLevel.PROTECTED) private double balance;
    private final ArrayList<Transaction> transactionHistory = new ArrayList<>();

    public List<Transaction> getTransactionHistory() {
        return List.copyOf(transactionHistory);
    }

    public void addTransactionToHistory(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public void topUpBalance(double amount) {
        balance += amount;
    }

    public void reduceBalance(double amount) throws CrossingBalanceAmountException {
        if (amount > balance) {
            throw new CrossingBalanceAmountException();
        }
        balance -= amount;
    }

    public void depositMoney(double amount) throws Exception {
        Transaction transaction = DepositTransactionFactory.createInstance(this, amount);
        getBank().commitTransaction(transaction);
        topUpBalance(amount);
    }
}
