package bank.services;

import bank.BankDatabase;
import com.mgargas.bank.GUID;
import com.mgargas.bank.InvalidGuid;
import com.mgargas.bank.Money;
import com.mgargas.bank.StandardManager;


public class StandardManagerService implements StandardManager.Iface {
    private BankDatabase bankDatabase;

    public StandardManagerService(BankDatabase bankDatabase) {
        this.bankDatabase = bankDatabase;
    }

    @Override
    public Money getBalance(GUID guid) throws InvalidGuid {
        return bankDatabase.getClient(guid).balance;
    }
}
