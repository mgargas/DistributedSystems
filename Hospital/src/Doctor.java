import com.rabbitmq.client.AMQP;

import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

public class Doctor extends HospitalWorker{
    static final String[] injuries = {"elbow", "hip", "knee"};
    private final String replyQueueName;
    private final AMQP.BasicProperties props;
    private Doctor() throws Exception{
        super();
        this.replyQueueName = channel.queueDeclare().getQueue();
        this.props = new AMQP.BasicProperties
                .Builder()
                .replyTo(replyQueueName)
                .build();
        registerConsumer(replyQueueName, "Received: ");
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
        channel.basicPublish(HOSPITAL_EXCHANGE, injury, props, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("Sent: " + message);
    }
}
