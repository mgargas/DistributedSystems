public class Technician extends HospitalWorker {
    private Technician() throws Exception{
        super();

    }
    public static void main(String[] args) throws Exception{
        Technician technician = new Technician();
        technician.registerConsumer(INJURY_QUEUE, "HEADER: ");
    }
}
