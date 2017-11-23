package entities.dto;

/**
 * Classic DTO
 * Represents a frame.
 * Due to protocol 1 frame can contain 1 message at a time, so there is no need for explicit synchronization.
 */
public class Message {

    private final int from;
    private final int to;
    private final String content;

    private long generated;
    private long sent;
    private long received;
    private long returned;


    public Message(int from, int to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public String toString() {
        return "Message<" + content + ">";
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public String content() {
        return content;
    }

    public long getSent() {
        return sent;
    }

    public void setSent() {
        this.sent = System.nanoTime();
    }

    public long getReceived() {
        return received;
    }

    public void setReceived() {
        this.received = System.nanoTime();
    }

    public long getReturned() {
        return returned;
    }

    public void setReturned() {
        this.returned = System.nanoTime();
    }

    public long getGenerated() {
        return generated;
    }

    public void setGenerated() {
        this.generated = System.nanoTime();
    }
}
