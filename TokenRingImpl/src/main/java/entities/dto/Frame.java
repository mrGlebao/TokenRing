package entities.dto;

/**
 * Classic DTO
 * Represents a frame.
 * Due to protocol 1 thread operates 1 frame at a time, so there is no need for explicit synchronization.
 */
public class Frame {

    private boolean isToken;

    private Message message;

    private Frame(boolean isToken) {
        this.isToken = isToken;
    }

    public static Frame createToken() {
        return new Frame(true);
    }

    public void setTokenFlag(boolean isToken) {
        this.isToken = isToken;
    }

    public boolean isEmptyToken() {
        return isToken && message == null;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Frame<" + message + ", isToken = " + isToken + ">";
    }

}