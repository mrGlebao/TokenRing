package entities;

import entities.dto.Frame;
import entities.dto.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.Utils.log;

public class Node extends Thread {

    private final int id;
    private final Operator operator;
    private List<Frame> frames = Collections.synchronizedList(new ArrayList<Frame>());
    private Node next;
    private List<Frame> myFrames = new ArrayList<>();

    public Node(int i) {
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

    public void setNext(Node node) {
        this.next = node;
    }

    public void sendMessage(Frame frame) {
        this.next.receiveMessage(frame);
    }

    /**
     * Получаешь сообщение, сохраняешь
     */
    public void receiveMessage(Frame frame) {
        frames.add(frame);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (!frames.isEmpty()) {
                Frame frame = frames.remove(0);
                operateFrame(frame);
            }
        }

    }

    /**
     * Здесь зашита логика управления фреймом.
     * Если это пустой токен:
     * оператору нечего сказать - отправляешь токен дальше.
     * оператору есть что сказать - генеришь сообщение и отправляешь его
     * Если это не токен:
     * Ты адресат - кладёшь его в свои сообщения, отправляешь дальше.
     * Ты отправитель - отправляешь дальше новый токен.
     * Else - Отправляешь.
     */
    private void operateFrame(Frame frame) {
        if (frame.isEmptyToken()) {
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
        } else {
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
}

