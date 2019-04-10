import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

public class Doctor extends HospitalWorker{
    static final String[] injuries = {"elbow", "hip", "knee"};
    private Doctor() throws Exception{
        super();
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
        channel.basicPublish(HOSPITAL_EXCHANGE, injury, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("Sent: " + message);
    }
}
