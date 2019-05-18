package bank.services;

import bank.BankDatabase;
import com.mgargas.bank.*;
import org.apache.thrift.TException;

public class PremiumManagerService implements PremiumManager.Iface {
    private BankDatabase bankDatabase;

    public PremiumManagerService(BankDatabase bankDatabase) {
        this.bankDatabase = bankDatabase;
    }

    @Override
    public LoanResponse askForLoan(LoanRequest loanRequest) throws InvalidGuid, InvalidAccount, InaccessibleCurrency {
        return bankDatabase.processLoanRequest(loanRequest);
    }

    @Override
    public Money getBalance(GUID guid) throws InvalidGuid {
        return bankDatabase.getClient(guid).balance;
    }
}
