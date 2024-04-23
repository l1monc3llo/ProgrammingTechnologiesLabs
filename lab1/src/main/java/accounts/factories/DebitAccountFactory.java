package accounts.factories;

import accounts.entities.DebitAccount;
import accounts.entities.IAccount;
import banks.entities.Bank;

public class DebitAccountFactory {
    public static IAccount createInstance(long id, Bank bank, long holderId) {
        return new DebitAccount(id, bank, holderId,0);
    }
}
