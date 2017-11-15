package entities;

import entities.dto.Frame;
import entities.dto.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static utils.Utils.log;

public class Node extends Thread {

    private final int id;
    private final Operator operator;
    private final Queue<Frame> frames;
    private Node next;
    private List<Frame> myFrames = new ArrayList<>();

    Node(int i) {
        this.frames = new ConcurrentLinkedQueue<>();
        this.id = i;
        this.operator = new Operator(i);
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

    void sendMessage(Frame frame) {
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
                operateFrame(frame);
            }
        }
    }

    private void operateFrame(Frame frame) {
        if (frame.isEmptyToken()) {
            operateEmptyToken(frame);
        } else {
            operateForeignMessage(frame);
        }
    }

    private void operateEmptyToken(Frame frame) {
        log("Node " + id + " received empty token");
        if (operator.hasMessageToSend()) {
            Message mess = operator.getMessage();
            log("Operator sent message from " + mess.from() + " to " + mess.to());
            frame.setMessage(mess);
            frame.setTokenFlag(false);
            sendMessage(frame);
        } else {
            log("operator " + id + " is silent. Node " + id + " sent empty token");
            sendMessage(frame);
        }
    }

    private void operateForeignMessage(Frame frame) {
        Message mess = frame.getMessage();
        log(id + " received message " + mess + " from " + mess.from() + " to " + mess.to());
        if (mess.from() == id) {
            log("Returned home!");
            sendMessage(Frame.createToken());
        } else if (mess.to() == id) {
            log("Went to addressee!");
            myFrames.add(frame);
            frame.setTokenFlag(false);
            sendMessage(frame);
        } else {
            log("Not mine!");
            sendMessage(frame);
        }
    }

}

