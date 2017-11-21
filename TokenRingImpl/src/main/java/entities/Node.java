package entities;

import entities.dto.Frame;
import entities.dto.Message;
import strategy.Strategy;
import strategy.StrategyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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
    private List<Message> myMessages = new ArrayList<>();
    private Strategy strategy;

    Node(int i) {
        this(i, StrategyType.DEFAULT);
    }

    Node(int i, StrategyType type) {
        this.frames = new ConcurrentLinkedQueue<>();
        this.id = i;
        this.operator = new Operator(i);
        this.strategy = StrategyType.getNodeStrategy(type, this);
        MessagesOverseer.register(this);
    }

    @Override
    public synchronized void start() {
        super.start();
        operator.start();
    }

    @Override
    public synchronized void interrupt() {
        super.interrupt();
        operator.interrupt();
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
        while (!isInterrupted() && Topology.topologyIsAlive()) {
            if (!frames.isEmpty()) {
                Frame frame = frames.poll();
                strategy.apply(frame);
            }
        }
    }

    public Operator getOperator() {
        return this.operator;
    }

    public void saveMessage(Frame frame) {
        Message mess = frame.getMessage();
        myMessages.add(new Message(mess.from(), mess.to(), mess.content()));
    }

    synchronized int numberOfReceivedFrames() {
        return myMessages.size();
    }

    @Override
    public String toString() {
        return "Node<id=" + id + ">";
    }

    public int getNodeId() {
        return id;
    }
}

