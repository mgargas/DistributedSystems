import org.jgroups.*;
import org.jgroups.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Vector;

public class DistributedMap extends ReceiverAdapter implements SimpleStringMap {
    final HashMap<String, Integer> contacts = new HashMap<>();
    private JChannel channel;
    private String user_name = System.getProperty("user.name", "n/a");

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
        handleView(channel, new_view);
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

    private static void handleView(JChannel ch, View new_view) {
        if(new_view instanceof MergeView) {
            ViewHandler handler=new ViewHandler(ch, (MergeView)new_view);
            // requires separate thread as we don't want to block JGroups
            handler.start();
        }
    }

    private static class ViewHandler extends Thread {
        JChannel ch;
        MergeView view;

        private ViewHandler(JChannel ch, MergeView view) {
            this.ch = ch;
            this.view = view;
        }

        public void run() {
            Vector<View> subgroups = (Vector<View>)view.getSubgroups();
            View tmp_view = subgroups.firstElement(); // picks the first
            Address local_addr = ch.getAddress();
            if (!tmp_view.getMembers().contains(local_addr)) {
                System.out.println("Not member of the new primary partition ("
                        + tmp_view + "), will re-acquire the state");
                try {
                    ch.getState(null, 30000);
                } catch (Exception ex) {
                }
            } else {
                System.out.println("Not member of the new primary partition ("
                        + tmp_view + "), will do nothing");
            }
        }
    }
}
