import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Technician extends InjurySpecialist {
    private List<String> specialisationsList = new LinkedList<>();

    private Technician() throws Exception {
        super();
    }

    public static void main(String[] args) throws Exception {
        Technician technician = new Technician();
        technician.setSpecialisationsList(Arrays.asList(args));
        technician.createQueues();
        technician.bindQueues();
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
                this.channel.queueBind(specialisation, INJURY_EXCHANGE, specialisation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setUpConsuming() {
        this.specialisationsList.forEach(specialisation -> {
            try {
                registerConsumer(specialisation, "Received: ", true, INJURY_EXCHANGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
