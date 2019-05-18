package bank;

import bank.services.AccountCreatorService;
import bank.services.PremiumManagerService;
import bank.services.StandardManagerService;
import com.mgargas.bank.*;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.*;

import java.util.ArrayList;
import java.util.Arrays;
public class AccountCreatorServer {

    public static void main(String[] args)
    {
        final int bankOfficePort = 9090;
        final int accountManagerPort = 9091;
        final int currencyExchangePort = 54321;
        BankDatabase bankDatabase =
                new BankDatabase(0.15, 10000, currencyExchangePort,
                        new ArrayList<>(Arrays.asList(Currency.PLN, Currency.GBP, Currency.USD)));
        new Thread(() -> {
            System.out.println(Thread.currentThread());
            TServerTransport t = null;
            try {
                t = new TServerSocket(bankOfficePort);
            } catch (TTransportException e) {
                e.printStackTrace();
            }
            TServer server = new TSimpleServer(new TServer.Args(t).processor(new AccountCreator.Processor<AccountCreator.Iface>(new AccountCreatorService(bankDatabase))));
            System.out.println("Starting the simple server...");
            server.serve();
        }).run();
        System.out.println("eldo");
        new Thread(() -> {
            System.out.println(Thread.currentThread());
            TMultiplexedProcessor processor = new TMultiplexedProcessor();

            processor.registerProcessor("PremiumManagerService",
                    new PremiumManager.Processor<PremiumManager.Iface>(new PremiumManagerService(bankDatabase)));
            processor.registerProcessor("StandardManagerService",
                    new StandardManager.Processor<StandardManager.Iface>(new StandardManagerService(bankDatabase)));

            TServerTransport serverTransport = null;
            try {
                serverTransport = new TServerSocket(accountManagerPort);
            } catch (TTransportException e) {
                e.printStackTrace();
            }

            TServer.Args tServerArgs = new TServer.Args(serverTransport);
            tServerArgs.processor(processor);

            TTransportFactory factory = new TFramedTransport.Factory();
            tServerArgs.transportFactory(factory);

            TSimpleServer server = new TSimpleServer(tServerArgs);

            System.out.println("Starting server on port " + accountManagerPort);
            server.serve();
        }).run();
    }
}
