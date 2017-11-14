package entities.dto;

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
        return "<" + content + ">";
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

}
