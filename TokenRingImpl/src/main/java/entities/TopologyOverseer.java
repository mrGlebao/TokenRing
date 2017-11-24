package entities;

import conf.Settings;
import entities.dto.Message;
import timestamp_writer.TimestampWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utils.Utils.log;

/**
 * This class knows exact number of messages sent? received and returned.
 * It is useful for creating conditions to terminate main thread.
 */
public class TopologyOverseer {

    private static final List<Node> registeredNodes = Collections.synchronizedList(new ArrayList<>());
    private static boolean topologyIsAlive = false;
    private static int messagesReceivedNumber = 0;
    private static int messagesSentNumber = 0;
    private static int messagesReturnedNumber = 0;
    private static int messagesGeneratedNumber = 0;
    private static int messagesOverheadedNumber = 0;

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
        //registeredNodes.stream().forEach(Node::printReceivedMessages);
//        System.out.println(registeredNodes.stream().mapToInt(Node::numberOfReceivedFrames).sum() == Settings.MESSAGES_TO_RECEIVE);
//        System.out.println(registeredNodes.stream().mapToInt(Node::numberOfReceivedFrames).sum());
//        System.out.println(numberOfMessagesGenerated());
//        System.out.println(numberOfMessagesOverheaded());
//        List<Message.Timestamps> list = registeredNodes.stream().map(Node::getMyMessages).map(elem -> elem.stream().map(Message::getTimestamps).collect(Collectors.toList())).collect(Collectors.toList());
        List<Message.Timestamps> stampsReceived = new ArrayList<>();
        for(Node n : registeredNodes) {
            List<Message.Timestamps> stampsTemp = n.getMyMessages().stream().map(Message::getTimestamps).collect(Collectors.toList());
            stampsReceived.addAll(stampsTemp);
        }
        TimestampWriter writer = new TimestampWriter();
        writer.write(stampsReceived);
    }
}
