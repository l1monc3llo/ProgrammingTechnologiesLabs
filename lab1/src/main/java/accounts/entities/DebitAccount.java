package accounts.entities;

import banks.entities.Bank;
import exceptions.CrossingBalanceAmountException;
import transactions.entities.Transaction;
import transactions.factories.TransferTransactionFactory;
import transactions.factories.WithdrawalTransactionFactory;

public class DebitAccount extends AbstractAccount {

    public DebitAccount(long id, Bank bank, long holderId, double balance) {
        super(id, bank, holderId, balance);
    }

    public void withdrawMoney(double amount) throws Exception {
        if (amount > getBalance()) {
            throw new CrossingBalanceAmountException();
        }
        Transaction transaction = WithdrawalTransactionFactory.createInstance(this, amount);
        getBank().commitTransaction(transaction);
        reduceBalance(amount);
    }

    public void transferMoney(IAccount account, double amount) throws Exception {
        if (amount > getBalance()) {
            throw new CrossingBalanceAmountException();
        }
        Transaction transaction = TransferTransactionFactory.createInstance(this, account, amount);
        getBank().commitTransaction(transaction);
        reduceBalance(amount);
        account.topUpBalance(amount);
    }
}
