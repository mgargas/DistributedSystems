import com.rabbitmq.client.*;

import java.io.IOException;

abstract public class HospitalWorker {
    Channel channel;
    static final boolean AUTO_ACK = false;
    final static String HOSPITAL_EXCHANGE = "hospital_exchange";
    final static String LOG_EXCHANGE = "logging_exchange";
    final static String LOG_SENDBOX = "log_sendbox";

    HospitalWorker() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(HOSPITAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(LOG_EXCHANGE, BuiltinExchangeType.FANOUT);
    }

    void registerConsumer(String queue, String messageHeader) throws Exception {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                try{
                    Thread.sleep(2000);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    String message = new String(body);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    System.out.println(messageHeader + message);
                }

            }
        };
        channel.basicConsume(queue, AUTO_ACK, consumer);
    }
}
