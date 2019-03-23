import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class DistributedMap extends ReceiverAdapter implements SimpleStringMap {
    final HashMap<String, Integer> contacts = new HashMap<>();
    JChannel channel;
    String user_name = System.getProperty("user.name", "n/a");

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    public void receive(Message msg) {
        String line = msg.getObject();
        if (msg.getSrc() != channel.address()) {
            String type = line.split(" ")[0];
            String key = line.split(" ")[1];
            System.out.println(line);
            switch (type) {
                case "p":
                    Integer value = Integer.valueOf(line.split(" ")[2]);
                    synchronized (contacts) {
                        contacts.put(key, value);
                    }
                    break;
                case "r":
                    synchronized (contacts) {
                        contacts.remove(key);
                    }
            }
        }
    }

    public void getState(OutputStream output) throws Exception {
        synchronized (contacts) {
            Util.objectToStream(contacts, new DataOutputStream(output));
        }
    }

    @SuppressWarnings("unchecked")
    public void setState(InputStream input) throws Exception {
        HashMap<String, Integer> contactsList = Util.objectFromStream(new DataInputStream(input));
        synchronized (contacts) {
            contacts.clear();
            contacts.putAll(contactsList);
        }
        System.out.println("received state (" + contactsList.size() + " contacts):");
        contactsList.entrySet().forEach(System.out::println);
    }


    void start() throws Exception {
        channel = new JChannel().setReceiver(this);
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
    }

    void stop() {
        channel.close();
    }

    @Override
    public boolean containsKey(String key) {
        return contacts.containsKey(key);
    }

    @Override
    public Integer get(String key) {
        return contacts.get(key);
    }

    @Override
    public void put(String key, Integer value) {
        synchronized (contacts) {
            contacts.put(key, value);
        }
        try {
            channel.send(new Message(null, "p" + " " + key + " " + value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer remove(String key) {
        Integer value;
        synchronized (contacts) {
            value = contacts.remove(key);
        }
        try {
            channel.send(new Message(null, "r" + " " + key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
