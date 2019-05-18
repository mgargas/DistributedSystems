package bank.services;

import bank.BankDatabase;
import com.mgargas.bank.*;
import org.apache.thrift.TException;

import java.util.UUID;

public class AccountCreatorService implements AccountCreator.Iface {
    private BankDatabase bankDatabase;

    public AccountCreatorService(BankDatabase bankDatabase) {
        this.bankDatabase = bankDatabase;
    }

    @Override
    public BankClient registerClient(UserData data, Money initialBalance) throws InvalidGuid {
        System.out.println("elo");
        BankClient newBankClient = new BankClient();
        newBankClient.setData(data);
        newBankClient.setBalance(initialBalance);
        AccountType type = bankDatabase.isPremium(data.income) ? AccountType.PREMIUM : AccountType.STANDARD;
        newBankClient.setType(type);
        GUID guid = new GUID(data.uid.toString());
        newBankClient.setGuid(guid);
        return bankDatabase.addClient(newBankClient);
    }
}
