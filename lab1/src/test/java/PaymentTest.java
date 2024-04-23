import accounts.entities.IAccount;
import banks.entities.Bank;
import banks.entities.CentralBank;
import clients.entities.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class PaymentTest {
    private CentralBank centralBank;
    private Bank bank;
    private Client client;
    private IAccount account;

    @BeforeEach
    public void init() {
        centralBank = new CentralBank();
        bank = new Bank(
                centralBank,
                "Alpha",
                0.01,
                amount -> {
                    double percent = 0;

                    if (amount < 100) percent = 0.03;
                    if (amount >= 100 && amount < 300) percent = 0.035;
                    if (amount >= 300) percent = 0.04;

                    return percent;
                },
                10,
                50,
                500000);
        centralBank.registerBank(bank);
        client = Client.builder()
                .firstName("Ivan")
                .lastName("Drunker")
                .build();
    }

    @Test
    public void shouldIncreaseRemains() {
        bank.registerDebitAccount(client.getId(), 100);
        account = bank.getAccounts().stream()
                .filter(account -> account.getHolderId() == client.getId())
                .findFirst()
                .orElseThrow();
        centralBank.skipDays(1);
        HashMap<IAccount, Double> remains = bank.getRemainders();
        Assertions.assertEquals(1.0, remains.get(account));
    }

    @Test
    public void shouldIncreaseAmount() {
        bank.registerDebitAccount(client.getId(), 100);
        account = bank.getAccounts().stream()
                .filter(account -> account.getHolderId() == client.getId())
                .findFirst()
                .orElseThrow();
        centralBank.skipMonths(1);
        Assertions.assertEquals(130.0, account.getBalance());
    }

    @Test
    public void shouldIncreaseRemainsForDeposit() {
        bank.registerDebitAccount(client.getId(), 250);
        account = bank.getAccounts().stream()
                .filter(account -> account.getHolderId() == client.getId())
                .findFirst()
                .orElseThrow();
        centralBank.skipDays(1);
        HashMap<IAccount, Double> remains = bank.getRemainders();
        Assertions.assertEquals(2.5, remains.get(account));
    }

    @Test
    public void shouldIncreaseAmountForDeposit() {
        bank.registerDebitAccount(client.getId(), 100);
        account = bank.getAccounts().stream()
                .filter(account -> account.getHolderId() == client.getId())
                .findFirst()
                .orElseThrow();
        centralBank.skipMonths(1);
        Assertions.assertEquals(130, account.getBalance());
    }
}
