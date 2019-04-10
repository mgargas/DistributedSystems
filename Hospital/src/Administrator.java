import java.util.stream.IntStream;

public class Administrator extends HospitalWorker{
    private Administrator() throws Exception{
        super();
        channel.queueDeclare(LOG_SENDBOX, true, false, false, null);
        registerConsumer(LOG_SENDBOX, "LOG: ");
    }

    public static void main(String[] args) throws Exception {
        Administrator administrator = new Administrator();
        IntStream.range(0, 10).forEach(value ->
        {
            String message = "INFORMATION NUMBER " + value;
            try {
                administrator.channel.basicPublish(LOG_EXCHANGE, "", null, message.getBytes());
                Thread.sleep(1500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
