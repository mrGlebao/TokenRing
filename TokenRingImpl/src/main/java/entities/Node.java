package entities;

import entities.dto.Frame;
import entities.dto.Message;
import strategy.node.NodeStrategy;
import strategy.node.StrategyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * An element of topology.
 * Node has its own operator, which generates messages, which the node
 * will try to send when it receives token.
 * Each node also has its strategy of managing the frames.
 */
public class Node extends Thread {

    private final int id;
    private final Operator operator;
    private final Queue<Frame> frames;
    private Node next;
    private List<Message> myMessages;
    private NodeStrategy nodeStrategy;

    Node(int i) {
        this(i, StrategyType.DEFAULT);
    }

    Node(int i, StrategyType type) {
        this.frames = new ConcurrentLinkedQueue<>();
        this.id = i;
        this.myMessages = new ArrayList<>();
        double verbosity = (new Random().nextInt(98) + 1) / 100.0;
        this.operator = new Operator(verbosity, i);
        this.nodeStrategy = StrategyType.getNodeStrategy(type, this);
        TopologyOverseer.register(this);
    }

    @Override
    public synchronized void start() {
        super.start();
        operator.start();
    }

    @Override
    public synchronized void interrupt() {
        if(!TopologyOverseer.topologyIsAlive()) {
            operator.interrupt();
            super.interrupt();
        }
    }

    void setNext(Node node) {
        this.next = node;
    }

    public void sendMessage(Frame frame) {
        this.next.receiveMessage(frame);
    }

    private void receiveMessage(Frame frame) {
        frames.add(frame);
    }

    @Override
    public void run() {
        while (!isInterrupted() && TopologyOverseer.topologyIsAlive()) {
            if (!frames.isEmpty()) {
                Frame frame = frames.poll();
                nodeStrategy.apply(frame);
            }
        }
    }

    public Operator getOperator() {
        return this.operator;
    }

    public void saveMessage(Frame frame) {
        Message mess = frame.getMessage();
        myMessages.add(mess);
    }

    public synchronized List<Message> getMyMessages() {
        return this.myMessages;
    }

    public synchronized Queue<Frame> getFrames() {
        return this.frames;
    }

    @Override
    public String toString() {
        return "Node<id=" + id + ">";
    }

    public int getNodeId() {
        return id;
    }
}

