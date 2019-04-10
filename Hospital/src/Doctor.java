import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

public class Doctor extends HospitalWorker{
    private Doctor() throws Exception{
        super();
    }

    public static void main(String[] args) {
        try {
            Doctor doctor = new Doctor();
            IntStream.range(0, 10).forEach(value -> {
                String message = "Elbow injury" + value;
                try {
                    doctor.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void sendMessage(String message) throws Exception{
        channel.basicPublish("", INJURY_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("Sent: " + message);
    }
}
