package entities;

import entities.dto.Frame;
import strategy.Strategy;
import strategy.VanillaTokenRingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Node extends Thread {

    private final int id;
    private final Operator operator;
    private final Queue<Frame> frames;
    private Node next;
    private List<Frame> myFrames = new ArrayList<>();
    private Strategy strategy;

    Node(int i) {
        this.frames = new ConcurrentLinkedQueue<>();
        this.id = i;
        this.operator = new Operator(i);
        this.strategy = new VanillaTokenRingStrategy(this);
        ReceivedMessagesOverseer.register(this);
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
        while (!isInterrupted()) {
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
        myFrames.add(frame);
    }

    public synchronized int numberOfReceivedFrames() {
        return myFrames.size();
    }

    public void printReceivedMessages() {
        myFrames.stream().map(Frame::getMessage).forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "Node<id=" + id + ">";
    }

    public int getNodeId() {
        return id;
    }
}

