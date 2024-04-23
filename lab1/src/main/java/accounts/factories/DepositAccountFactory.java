package accounts.factories;

import accounts.entities.DepositAccount;
import accounts.entities.IAccount;
import banks.entities.Bank;

public class DepositAccountFactory {
    public static IAccount createInstance(long id, Bank bank, long holderId, double balance) {
        return new DepositAccount(id, bank, holderId, balance);
    }
}
