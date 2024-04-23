package transactions.factories;

import accounts.entities.IAccount;
import transactions.entities.Transaction;
import transactions.enums.TransactionType;

public class DepositTransactionFactory {
    public static Transaction createInstance(
            IAccount account,
            double amount) {
        return new Transaction(account, account, TransactionType.Deposit, amount);
    }
}
