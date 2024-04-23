package transactions.entities;

import accounts.entities.IAccount;
import lombok.Getter;
import lombok.Setter;
import transactions.enums.TransactionStatus;
import transactions.enums.TransactionType;

@Getter
public class Transaction {
    private final IAccount senderAccount;
    private final IAccount receiverAccount;
    private final double amount;
    private final TransactionType type;
    @Setter private TransactionStatus status;
    public Transaction(
            IAccount senderAccount,
            IAccount receiverAccount,
            TransactionType type,
            double amount) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.type = type;
        this.amount = amount;
        status = TransactionStatus.Active;
    }
}
