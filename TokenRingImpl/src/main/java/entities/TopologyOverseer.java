package entities;

import entities.dto.Message;
import timestamp_writer.TimestampWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class knows exact number of messages sent? received and returned.
 * It is useful for creating conditions to terminate main thread.
 */
public class TopologyOverseer {

    private static List<Node> registeredNodes = Collections.synchronizedList(new ArrayList<>());
    private static boolean topologyIsAlive = false;
    private static int messagesReceivedNumber = 0;
    private static int messagesSentNumber = 0;
    private static int messagesReturnedNumber = 0;
    private static int messagesGeneratedNumber = 0;
    private static int messagesOverheadedNumber = 0;

    public static void clear() {
        registeredNodes.clear();
        topologyIsAlive = false;
        messagesReceivedNumber = 0;
        messagesSentNumber = 0;
        messagesGeneratedNumber = 0;
        messagesOverheadedNumber = 0;
        messagesGeneratedNumber = 0;
    }

    public static synchronized int numberOfMessagesReceived() {
        return messagesReceivedNumber;
    }

    public static synchronized void incrementReceived() {
        messagesReceivedNumber = messagesReceivedNumber + 1;
    }

    public static synchronized int numberOfMessagesGenerated() {
        return messagesGeneratedNumber;
    }

    public static synchronized void incrementGenerated() {
        messagesGeneratedNumber = messagesGeneratedNumber + 1;
    }

    public static int numberOfMessagesOverheaded() {
        return messagesOverheadedNumber;
    }

    public static void incrementOverheaded() {
        messagesOverheadedNumber = messagesOverheadedNumber + 1;
    }

    public static synchronized int numberOfMessagesSent() {
        return messagesSentNumber;
    }

    public static synchronized void incrementSent() {
        messagesSentNumber = messagesSentNumber + 1;
    }

    public static synchronized int numberOfMessagesReturned() {
        return messagesReturnedNumber;
    }

    public static synchronized boolean topologyIsAlive() {
        return topologyIsAlive;
    }

    public static synchronized void setTopologyIsAliveFlag(boolean bool) {
        topologyIsAlive = bool;
    }

    public static synchronized void incrementReturned() {
        messagesReturnedNumber = messagesReturnedNumber + 1;
    }

    static synchronized void register(Node node) {
        registeredNodes.add(node);
    }

    public static synchronized void printAllReceivedStamps() {
        List<Message.Timestamps> stampsReceived = new ArrayList<>();
        for (Node n : registeredNodes) {
            List<Message.Timestamps> stampsTemp = n.getMyMessages().stream().map(Message::getTimestamps).collect(Collectors.toList());
            stampsReceived.addAll(stampsTemp);
        }
        TimestampWriter writer = new TimestampWriter();
        writer.write(stampsReceived);
    }
}
