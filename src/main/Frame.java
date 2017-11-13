package main;

public class Frame {

    private volatile boolean isToken;
    private Message message;

    private Frame(boolean isToken) {
        this.isToken = isToken;
    }

    public static Frame createToken() {
        return new Frame(true);
    }

    public boolean isTokenFlag() {
        return isToken;
    }

    // ToDo: setTokenFlagToFalse
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

}
