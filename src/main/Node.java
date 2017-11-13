import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node extends Thread {

    public final int id;
    private final Operator operator;
    public List<Frame> frames = Collections.synchronizedList(new ArrayList<Frame>());
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
    public void receiveMessage(Frame frame)  {
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
            System.out.println("Node "+id+" received empty token");
            if (operator.hasMessageToSend()) {
                Message mess = operator.getMessage();
                System.out.println("Operator sent message from "+mess.from+" to "+mess.to);
                frame.setMessage(mess);
                frame.setTokenFlag(false);
                sendMessage(frame);
            } else {
                System.out.println("operator "+id+" is silent. Node "+id+" sent empty token");
                sendMessage(frame);
            }
        } else {
            Message mess = frame.getMessage();
            System.out.println(id + " received message " + mess + " from " + mess.from + " to " + mess.to);
            if (mess.from == id) {
                System.out.println("Returned home!");
                sendMessage(Frame.createToken());
            } else if (mess.to == id) {
                System.out.println("Went to addressee!");
                myFrames.add(frame);
                frame.setTokenFlag(false);
                sendMessage(frame);
            } else {
                System.out.println("Not mine!");
                sendMessage(frame);
            }

        }
    }
}

