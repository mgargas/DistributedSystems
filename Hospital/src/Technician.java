import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Technician extends HospitalWorker {
    private List<String> specialisationsList = new LinkedList<>();
    private Consumer consumer;
    private Technician() throws Exception {
        super();

    }

    public static void main(String[] args) throws Exception {
        Technician technician = new Technician();
        technician.setSpecialisationsList(Arrays.asList(args));
        technician.createQueues();
        technician.bindQueues();
        technician.consumer = new DefaultConsumer(technician.channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                try{
                    Thread.sleep(2500);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    String message = new String(body);
                    technician.channel.basicAck(envelope.getDeliveryTag(), false);
                    System.out.println("Technician did: " + message);
                }

            }
        };
        technician.setUpConsuming();
    }

    private void setSpecialisationsList(List<String> specialisationsList) {
        this.specialisationsList = specialisationsList;
    }

    private void createQueues() {
        this.specialisationsList.forEach(specialisation -> {
            try {
                this.channel.queueDeclare(specialisation, true, false, false, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void bindQueues() {
        this.specialisationsList.forEach(specialisation -> {
            try {
                this.channel.queueBind(specialisation, HOSPITAL_EXCHANGE, specialisation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setUpConsuming() {
        this.specialisationsList.forEach(queue -> {
            try {
                this.channel.basicConsume(queue, AUTO_ACK, this.consumer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
