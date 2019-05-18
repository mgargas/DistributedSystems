package exchange;

import com.mgargas.currencyexchange.*;
import com.mgargas.currencyexchange.Currency;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CurrencyExchangeClient {
    private static final Logger logger = Logger.getLogger(CurrencyExchangeClient.class.getName());
    private final ManagedChannel channel;
    private final CurrencyExchangeServiceGrpc.CurrencyExchangeServiceBlockingStub blockingStub;
    private final CurrencyExchangeServiceGrpc.CurrencyExchangeServiceStub asyncStub;
    private Map<Currency, Double> currencyRateMap = new HashMap<>();

    /**
     * Construct client for accessing RouteGuide server at {@code host:port}.
     */
    public CurrencyExchangeClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    /**
     * Construct client for accessing RouteGuide server using the existing channel.
     */
    private CurrencyExchangeClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = CurrencyExchangeServiceGrpc.newBlockingStub(channel);
        asyncStub = CurrencyExchangeServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public CurrencyRates getCurrencyRateList(List<Currency> currencies) {
        CurrencyRequest request = CurrencyRequest.newBuilder()
                .addAllCurrency(currencies)
                .build();
        CurrencyRates currencyRates = blockingStub.getForeignCurrenciesRates(request);
        currencyRates.getCurrencyRateList()
                .forEach(currencyRate -> currencyRateMap.put(currencyRate.getCurrency(), currencyRate.getExchangeRate()));
        return currencyRates;
    }

    public void getCurrencyRateStream(List<Currency> currencies) {
        CurrencyRequest request = CurrencyRequest.newBuilder()
                .addAllCurrency(currencies)
                .build();
        Iterator<CurrencyRate> currencyIterator = blockingStub.getForeignCurrenciesStream(request);
        while (currencyIterator.hasNext()) {
            CurrencyRate next = currencyIterator.next();
            currencyRateMap.put(next.getCurrency(), next.getExchangeRate());
            logger.info(next.toString());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CurrencyExchangeClient client = new CurrencyExchangeClient("localhost", 54321);
        try {
            client.getCurrencyRateList(new LinkedList<>(Arrays.asList(Currency.GBP, Currency.PLN)));
            client.getCurrencyRateStream(new LinkedList<>(Arrays.asList(Currency.GBP, Currency.PLN)));
        } finally {
            client.shutdown();
        }
    }
}
