import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

abstract public class SystemWorker {
    static final boolean AUTO_ACK = false;
    final static String INJURY_EXCHANGE = "injury_exchange";
    final static String INFO_EXCHANGE = "info_exchange";
    final static String INJURY_LOGGING_QUEUE = "logging_queue";
    final static String REQUEST_BINDING_KEY = "request.*";
    final static String REPLY_BINDING_KEY = "reply.#";
    Channel channel;

    SystemWorker() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();
        this.channel.exchangeDeclare(INJURY_EXCHANGE, BuiltinExchangeType.TOPIC);
        this.channel.queueDeclare(INJURY_LOGGING_QUEUE, true, false, false, null);
        channel.queueBind(INJURY_LOGGING_QUEUE, INJURY_EXCHANGE, REQUEST_BINDING_KEY);
        channel.queueBind(INJURY_LOGGING_QUEUE, INJURY_EXCHANGE, REPLY_BINDING_KEY);
//        this.channel.queueBind(INJURY_LOGGING_QUEUE, INJURY_EXCHANGE, "#");
        this.channel.exchangeDeclare(INFO_EXCHANGE, BuiltinExchangeType.FANOUT);
    }

    void registerConsumer(String queue, String messageHeader, boolean shouldReply, String replyExchange) throws Exception {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep((long) (Math.random() * 1000) + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    String message = new String(body);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    System.out.println(messageHeader + message);
                    if (shouldReply) {
                        channel.basicPublish(replyExchange, "reply." + properties.getReplyTo(), null, (message + "reply").getBytes(StandardCharsets.UTF_8));
                    }
                }

            }
        };
        channel.basicConsume(queue, AUTO_ACK, consumer);
    }
}
