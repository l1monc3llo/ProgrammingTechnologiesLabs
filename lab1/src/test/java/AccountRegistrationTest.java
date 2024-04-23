import static org.junit.jupiter.api.Assertions.assertTrue;

import banks.entities.Bank;
import banks.entities.CentralBank;
import clients.entities.Client;
import org.junit.jupiter.api.Test;

public class AccountRegistrationTest {
    private final Client client = Client.builder()
            .firstName("Ivan")
            .lastName("Drunker")
            .build();
    private final CentralBank centralBank = new CentralBank();
    private final Bank bank = new Bank(
            centralBank,
            "Alpha",
            0.3,
            (amount -> 0.7),
            270,
            20,
            500000);

    @Test
    public void shouldRegisterAccount() {
        centralBank.registerBank(bank);
        bank.registerClient(client);
        bank.registerDebitAccount(client.getId(), 0);
        assertTrue(bank.getAccounts().stream().anyMatch(account -> account.getHolderId() == client.getId()));
    }
}
