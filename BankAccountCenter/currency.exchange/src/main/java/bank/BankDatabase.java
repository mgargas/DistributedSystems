package bank;
import com.mgargas.bank.*;
import com.mgargas.currencyexchange.CurrencyRates;
import exchange.CurrencyExchangeClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BankDatabase {
    private final double interestRate;
    private final long premiumMinIncome;
    private final int currencyExchangePort;
    private final CurrencyExchangeClient exchangeClient;
    private final List<Currency> availableCurrencies;
    private Map<GUID, BankClient> clients;


    public BankDatabase(double interestRate, long premiumMinIncome, int currencyExchangePort, List<Currency> currencies) {
        this.interestRate = interestRate;
        this.premiumMinIncome = premiumMinIncome;
        this.currencyExchangePort = currencyExchangePort;
        this.exchangeClient = new CurrencyExchangeClient("localhost", this.currencyExchangePort);
        this.availableCurrencies = currencies;
        this.clients = new HashMap<>();
    }

    public BankClient addClient(BankClient client) throws InvalidGuid {
        if(clients.containsKey(client.guid)) {
            throw new InvalidGuid(client.guid, "Client with this GUID already exists.");
        } else {
            clients.put(client.guid, client);
            return client;
        }
    }

    public BankClient getClient(GUID guid) throws InvalidGuid {
        if(clients.containsKey(guid)) {
            throw new InvalidGuid(guid, "Client with this GUID does not exist.");
        } else {
            return clients.get(guid);
        }
    }

    public LoanResponse processLoanRequest(LoanRequest loanRequest) throws InvalidGuid, InvalidAccount, InaccessibleCurrency {
        BankClient client = getClient(loanRequest.guid);
        if(client.type != AccountType.PREMIUM) throw new InvalidAccount();
        Optional<Integer> currencyCode = availableCurrencies.stream().map(Currency::getValue).filter(v -> v == loanRequest.currency.getValue()).findAny();
        if(!currencyCode.isPresent()) throw new InaccessibleCurrency().setAvailableCurrencies(availableCurrencies);
        CurrencyRates rates = exchangeClient.getCurrencyRateList(availableCurrencies.stream().map(c -> com.mgargas.currencyexchange.Currency.forNumber(c.getValue())).collect(Collectors.toList()));
        double originCost = rates.getCurrencyRate(0).getExchangeRate() * (1 + interestRate) * loanRequest.period.months;
        double foreignCost = rates.getCurrencyRate(loanRequest.currency.getValue()).getExchangeRate() * (1 + interestRate) * loanRequest.period.months;
        return new LoanResponse()
                .setAccepted(true)
                .setBaseCost(new Money((long) originCost))
                .setTargetCost(new Money((long) foreignCost));
    }

    public boolean isPremium(Money income) {
        return income.value > this.premiumMinIncome;
    }

    public CurrencyExchangeClient getExchangeClient() {
        return exchangeClient;
    }
}
