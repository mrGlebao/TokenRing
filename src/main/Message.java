package main;

public class Message {
    public int from;
    public int to;
    public String content;

    public Message(int from, int to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public String toString() {
        return "<"+content+">";
    }

}
