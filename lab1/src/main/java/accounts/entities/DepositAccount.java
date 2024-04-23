package accounts.entities;

import accounts.models.DepositAccountStatus;
import banks.entities.Bank;
import lombok.Getter;
import lombok.Setter;
import transactions.entities.Transaction;
import transactions.factories.TransferTransactionFactory;
import transactions.factories.WithdrawalTransactionFactory;

@Getter
public class DepositAccount extends AbstractAccount {
    @Setter private DepositAccountStatus status;

    public DepositAccount(long id, Bank bank, long holderId, double balance) {
        super(id, bank, holderId, balance);
        this.status = DepositAccountStatus.Active;
    }

    public void withdrawMoney(double amount) throws Exception {
        if (status != DepositAccountStatus.Active) {
            throw new UnsupportedOperationException("The deposit account is still not expired to withdraw");
        }
        Transaction transaction = WithdrawalTransactionFactory.createInstance(this, amount);
        getBank().commitTransaction(transaction);
        reduceBalance(amount);
    }

    public void transferMoney(IAccount account, double amount) throws Exception {
        if (status == DepositAccountStatus.Active) {
            throw new UnsupportedOperationException("The deposit account is still not expired to transfer");
        }
        Transaction transaction = TransferTransactionFactory.createInstance(this, account, amount);
        getBank().commitTransaction(transaction);
        reduceBalance(amount);
        account.topUpBalance(amount);
    }

    public void changeStatus(DepositAccountStatus newStatus){
        if (newStatus == status){
            throw new UnsupportedOperationException("Current status is already defined");
        }
        setStatus(newStatus);
    }
}