import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Operator extends Thread {

    private List<Message> messagesToSend =  Collections.synchronizedList(new ArrayList<>());
    public final double verbose;
    public final int nodeId;

    public Operator(int nodeId) {
        this.nodeId = nodeId;
        this.verbose = Settings.VERBOSE_DEFAULT;
    }

    public Operator(double verbose, int nodeId) {
        this.verbose = verbose;
        this.nodeId = nodeId;
    }

    private int generateAddresate() {
        while (true) {
            int to_temp = (int) (Settings.TOPOLOGY_SIZE * Math.random());
            if (to_temp != this.nodeId)
                return to_temp;
        }
    }

    private void prepareMessage() {
        int to = generateAddresate();
        String message = "from op" + nodeId + " to op" + to + " message: you are stinky!";
        messagesToSend.add(new Message(nodeId, to, message));
    }

    public synchronized boolean hasMessageToSend() {
        return !messagesToSend.isEmpty();
    }

    public synchronized Message getMessage() {
        return messagesToSend.remove(0);
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            if (this.verbose > Math.random()) {
                System.out.println("Operator ID" + nodeId +" GENERATES MESSAGE!");
                prepareMessage();
            }
            try {
                Thread.sleep(Settings.OPERATOR_SLEEP_DEFAULT);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

}
