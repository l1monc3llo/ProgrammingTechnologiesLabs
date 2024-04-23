package accounts.factories;

import accounts.entities.CreditAccount;
import accounts.entities.IAccount;
import banks.entities.Bank;

public class CreditAccountFactory {
    public static IAccount createInstance(long id, Bank bank, long holderId, double balance) {
        return new CreditAccount(id, bank, holderId, balance);
    }
}
