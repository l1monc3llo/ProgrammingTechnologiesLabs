package transactions.factories;

import accounts.entities.IAccount;
import transactions.entities.Transaction;
import transactions.enums.TransactionType;

public class TransferTransactionFactory {
    public static Transaction createInstance(
            IAccount senderAccount,
            IAccount receiverAccount,
            double amount) {
        return new Transaction(senderAccount, receiverAccount, TransactionType.Transfer, amount);
    }
}
