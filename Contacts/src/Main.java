import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        DistributedMap distributedMap = new DistributedMap();
        distributedMap.start();
        eventLoop(distributedMap);
        distributedMap.stop();
    }

    private static void eventLoop(DistributedMap distributedMap) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.print("> ");
                System.out.flush();
                String line = in.readLine();
                if (line.startsWith("quit") || line.startsWith("exit")) {
                    break;
                }
                String type = line.split(" ")[0];
                String key = line.split(" ")[1];
                switch (type) {
                    case "c":
                        distributedMap.containsKey(key);
                        break;
                    case "g":
                        distributedMap.get(key);
                        break;
                    case "r":
                        distributedMap.remove(key);
                        break;
                    case "p":
                        Integer value = Integer.valueOf(line.split(" ")[2]);
                        distributedMap.put(key, value);
                        break;
                    case "s":
                        distributedMap.contacts.entrySet().forEach(System.out::println);
                }

            } catch (Exception e) {
            }
        }
    }

}
