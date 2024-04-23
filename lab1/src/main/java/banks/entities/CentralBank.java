package banks.entities;
import accounts.entities.IAccount;
import transactions.entities.Transaction;
import transactions.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static transactions.enums.TransactionStatus.Canceled;

@Getter
@Setter
public class CentralBank {
    private static long bankIdMarker = 0;
    private final List<Bank> banks = new ArrayList<>();

    public void registerBank(Bank bank) {
        bank.setId(bankIdMarker);
        ++bankIdMarker;
        banks.add(bank);
    }

    public void skipDays(int days) {
        for (int i = 0; i < days; i++)
            banks.forEach(Bank::updateRemains);
    }

    public void skipWeeks(int weeks) {
        skipDays(weeks * 7);
    }

    public void skipMonths(int months) {
        skipDays(months * 30);

        banks.forEach(Bank::payRemains);
        banks.forEach(Bank::takeCommissions);
    }

    public void skipYears(int years) {
        int months = years * 12;
        skipMonths(months);

        for (int i = 0; i < months; i++) {
            banks.forEach(Bank::payRemains);
            banks.forEach(Bank::takeCommissions);
        }
    }

    public void commitTransaction(Transaction transaction) throws Exception {
        transaction.setStatus(TransactionStatus.Committed);
    }

    public void cancelTransaction(Transaction transaction) throws Exception {
        IAccount receiverAccount = transaction.getReceiverAccount();
        IAccount senderAccount = transaction.getSenderAccount();
        if (transaction.getStatus() == TransactionStatus.Canceled){
            return;
        }
        switch (transaction.getType()) {
            case Deposit:
                receiverAccount.reduceBalance(transaction.getAmount());
                break;
            case Withdrawal:
                receiverAccount.topUpBalance(transaction.getAmount());
                break;
            case Transfer:
                receiverAccount.reduceBalance(transaction.getAmount());
                senderAccount.topUpBalance(transaction.getAmount());
                break;
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }
        transaction.setStatus(Canceled);
    }
}
