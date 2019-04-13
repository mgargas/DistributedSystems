import java.util.stream.IntStream;

public class Administrator extends SystemWorker {
    static final String LOG_HEADER = "LOG: ";
    private Administrator() throws Exception{
        super();
        registerConsumer(INJURY_LOGGING_QUEUE, LOG_HEADER, false, "");
    }

    public static void main(String[] args) throws Exception {
        Administrator administrator = new Administrator();
        IntStream.range(0, 20).forEach(value ->
        {
            String message = "INFORMATION NUMBER " + value;
            try {
                administrator.channel.basicPublish(INFO_EXCHANGE, "", null, message.getBytes());
                Thread.sleep(1500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
