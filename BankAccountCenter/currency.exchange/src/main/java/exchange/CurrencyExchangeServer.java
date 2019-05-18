package exchange;

import com.mgargas.currencyexchange.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CurrencyExchangeServer {
    private static final Logger logger = Logger.getLogger(CurrencyExchangeServer.class.getName());
    private final int port;
    private Server server;


    public CurrencyExchangeServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new ExchangeCurrencyService())
                .build();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final CurrencyExchangeServer currencyExchangeServer = new CurrencyExchangeServer(54321);
        currencyExchangeServer.start();
        currencyExchangeServer.blockUntilShutdown();
    }

    /**
     * Start serving requests.
     */
    public void start() throws IOException {
        server.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may has been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            CurrencyExchangeServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    /**
     * Stop serving requests and shutdown resources.
     */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private static class ExchangeCurrencyService extends CurrencyExchangeServiceGrpc.CurrencyExchangeServiceImplBase {

        private static CurrencyRate generateCurrencyReply(Currency currency) {
            Random random = new Random();
            double rate = random.nextDouble() * 2 + 3;
            return CurrencyRate.newBuilder()
                    .setCurrency(currency)
                    .setExchangeRate(rate)
                    .build();
        }

        @Override
        public void getForeignCurrenciesRates(CurrencyRequest request, StreamObserver<CurrencyRates> responseObserver) {
            responseObserver.onNext(
                    CurrencyRates.newBuilder()
                            .addAllCurrencyRate(request.getCurrencyList().stream()
                                    .map(ExchangeCurrencyService::generateCurrencyReply)
                                    .collect(Collectors.toList()))
                            .build());
            responseObserver.onCompleted();
        }

        @Override
        public void getForeignCurrenciesStream(CurrencyRequest request, StreamObserver<CurrencyRate> responseObserver) {
            while (true) {
                request.getCurrencyList().forEach(currency -> {
                    try {
                        Thread.sleep((long) (Math.random() * 5000 + 2));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    responseObserver.onNext(generateCurrencyReply(currency));
                });
            }
        }
    }
}
