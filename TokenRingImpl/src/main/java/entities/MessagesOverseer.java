package entities;

import conf.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class knows exact number of messages created and received.
 * It is useful for creating conditions to terminate main thread.
 */
public class MessagesOverseer {

    private static final List<Node> registeredNodes = Collections.synchronizedList(new ArrayList<>());
    private static int messagesReceivedNumber = 0;
    private static int messagesSentNumber = 0;
    private static int messagesReturnedNumber = 0;

    public static synchronized int numberOfMessagesReceived() {
        return messagesReceivedNumber;
    }

    public static synchronized void incrementReceived() {
        messagesReceivedNumber = messagesReceivedNumber + 1;
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

    public static synchronized void incrementReturned() {
        messagesReturnedNumber = messagesReturnedNumber + 1;
    }

    static synchronized void register(Node node) {
        registeredNodes.add(node);
    }

    public static synchronized void printAllReceivedMessages() {
         //registeredNodes.stream().forEach(Node::printReceivedMessages);
        System.out.println(registeredNodes.stream().mapToInt(Node::numberOfReceivedFrames).sum() == Settings.MESSAGES_TO_RECEIVE);
        System.out.println(registeredNodes.stream().mapToInt(Node::numberOfReceivedFrames).sum());
    }


}
