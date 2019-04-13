import com.rabbitmq.client.AMQP;

import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

public class Doctor extends InjurySpecialist {
    private static final String[] injuries = {"elbow", "hip", "knee"};
    private final String replyQueueName;
    private final AMQP.BasicProperties props;
    private Doctor() throws Exception{
        super();
        this.replyQueueName = this.channel.queueDeclare().getQueue();
        this.props = new AMQP.BasicProperties
                .Builder()
                .replyTo(replyQueueName)
                .build();
        this.channel.queueBind(this.replyQueueName, INJURY_EXCHANGE, this.replyQueueName);
        registerConsumer(this.replyQueueName, "Received: ", false, "");
    }

    public static void main(String[] args) {
        try {
            Doctor doctor = new Doctor();
            IntStream.range(0, 10).forEach(value -> {
                int index = (int)(Math.random() * 10)%3;
                String message = injuries[index] + value;
                try {
                    doctor.sendMessage(message, injuries[index]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void sendMessage(String message, String injury) throws Exception{
        channel.basicPublish(INJURY_EXCHANGE, injury, props, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("Sent: " + message);
    }
}
