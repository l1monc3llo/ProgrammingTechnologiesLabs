import accounts.entities.IAccount;
import banks.entities.Bank;
import banks.entities.CentralBank;
import clients.entities.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transactions.entities.Transaction;
import transactions.enums.TransactionStatus;

public class TransactionsTest {
    private Bank bank;
    private CentralBank centralBank;
    private Client clientSender;
    private Client clientReceiver;
    private IAccount senderAccount;
    private IAccount receiverAccount;
    @BeforeEach
    public void init() {
        centralBank = new CentralBank();
        bank = new Bank(
                centralBank,
                "Alpha",
                0.4,
                (amount -> 0.1),
                270,
                20,
                500000);
        centralBank.registerBank(bank);
        clientSender = Client.builder()
                .firstName("Ivan")
                .lastName("Drunker")
                .build();
        clientReceiver = Client.builder()
                .firstName("Sergey")
                .lastName("Utkin")
                .build();
        bank.registerClient(clientSender);
        bank.registerClient(clientReceiver);
        bank.registerDebitAccount(clientSender.getId(), 2000);
        bank.registerDebitAccount(clientReceiver.getId(), 230);
        senderAccount = bank.getAccounts().stream()
                .filter(account -> account.getHolderId() == clientSender.getId())
                .findFirst()
                .orElseThrow();

        receiverAccount = bank.getAccounts().stream()
                .filter(account -> account.getHolderId() == clientReceiver.getId())
                .findFirst()
                .orElseThrow();
    }
    @Test
    public void shouldUpdateBalanceForBoth() throws Exception {
        senderAccount.transferMoney(receiverAccount, 100);
        Assertions.assertEquals(1900, senderAccount.getBalance());
        Assertions.assertEquals(330, receiverAccount.getBalance());
    }

    @Test
    public void shouldCancelTransaction() throws Exception {
        senderAccount.transferMoney(receiverAccount, 100);
        Assertions.assertEquals(1900, senderAccount.getBalance());
        Assertions.assertEquals(330, receiverAccount.getBalance());
        Transaction transaction = senderAccount.getTransactionHistory().get(0);
        bank.cancelTransaction(transaction);
        Assertions.assertEquals(2000, senderAccount.getBalance());
        Assertions.assertEquals(230, receiverAccount.getBalance());
        Assertions.assertEquals(TransactionStatus.Canceled, transaction.getStatus());
    }
}
