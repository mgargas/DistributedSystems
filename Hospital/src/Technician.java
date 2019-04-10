import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.HashMap;

public class Technician extends HospitalWorker {
    private HashMap<String, String> specialisationsQueues = new HashMap<>();

    private Technician() throws Exception {
        super();

    }

    public static void main(String[] args) throws Exception {
        Technician technician = new Technician();
        technician.setSpecialisationsQueues(args);
        technician.bindQueues();
        Consumer consumer = new DefaultConsumer(technician.channel) {
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
        technician.specialisationsQueues.values().forEach(queue -> {
            try {
                technician.channel.basicConsume(queue, AUTO_ACK, consumer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setSpecialisationsQueues(String[] specialisations) throws Exception {
        for (String specialisation : specialisations) {
            //String specialisationQueue = this.channel.queueDeclare().getQueue();
            this.channel.queueDeclare(specialisation, true, false, false, null);
            this.specialisationsQueues.put(specialisation, specialisation);
        }
    }

    private void bindQueues() throws IOException {
        this.specialisationsQueues.forEach((specialisation, queue) -> {
            try {
                this.channel.queueBind(queue, HOSPITAL_EXCHANGE, specialisation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
