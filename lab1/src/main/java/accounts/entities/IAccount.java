package accounts.entities;

import banks.entities.Bank;
import exceptions.CrossingBalanceAmountException;
import transactions.entities.Transaction;

import java.util.List;

public interface IAccount {
    long getId();
    Bank getBank();
    long getHolderId();
    double getBalance();
    List<Transaction> getTransactionHistory();
    void addTransactionToHistory(Transaction transaction);
    default void topUpBalance(double amount) {};
    default void reduceBalance(double amount) throws CrossingBalanceAmountException {};
    void depositMoney(double amount) throws Exception;
    void withdrawMoney(double amount) throws Exception;
    void transferMoney(IAccount account, double amount) throws Exception;
}