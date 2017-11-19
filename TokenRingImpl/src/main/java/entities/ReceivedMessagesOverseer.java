package entities;

import java.util.ArrayList;
import java.util.List;

public class ReceivedMessagesOverseer {

    private static final List<Node> registeredNodes = new ArrayList<>();

    static synchronized void register(Node node) {
        registeredNodes.add(node);
    }

    public static synchronized int numberOfMessagesReceived() {
        return registeredNodes.stream().mapToInt(Node::numberOfReceivedFrames).sum();
    }

    public static synchronized void printAllReceivedMessages() {
        registeredNodes.stream().forEach(Node::printReceivedMessages);
    }


}
