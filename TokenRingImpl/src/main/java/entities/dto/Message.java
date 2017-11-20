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

}
