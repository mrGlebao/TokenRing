package entities;

import conf.Settings;
import entities.dto.Message;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static utils.Utils.log;

public class Operator extends Thread {

    private final Queue<Message> messagesToSend;
    private final double verbose;
    private final int nodeId;

    Operator(int nodeId) {
        this(Settings.VERBOSE_DEFAULT, nodeId);
    }

    private Operator(double verbose, int nodeId) {
        this.messagesToSend  = new ConcurrentLinkedQueue<>();
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
        return messagesToSend.poll();
    }

    public int getOperatorId() {
        return this.nodeId;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            if (this.verbose > Math.random()) {
                log("Operator ID" + nodeId +" GENERATES MESSAGE!");
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
