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

    private Timestamps timestamps;

    public Message(int from, int to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.timestamps = new Timestamps();
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

    public Timestamps getTimestamps() {
        return this.timestamps;
    }

    public void setSent() {
        this.timestamps.sent = System.nanoTime();
    }

    public void setReceived() {
        this.timestamps.received = System.nanoTime();
    }

    public void setReturned() {
        this.timestamps.returned = System.nanoTime();
    }

    public void setGenerated() {
        this.timestamps.generated = System.nanoTime();
    }

    public class Timestamps {
        private long generated;
        private long sent;
        private long received;
        private long returned;

        public long getSent() {
            return this.sent;
        }

        public long getReceived() {
            return this.received;
        }

        public long getReturned() {
            return this.returned;
        }

        public long getGenerated() {
            return this.generated;
        }

        @Override
        public String toString() {
            return "<Stamp\n"+
                    "Generated: "+getGenerated()+"\n"+
                    "Sent: "+getSent()+"\n"+
                    "Received: "+getReceived()+"\n"+
                    "Returned: "+getReturned()+"\n>" +
                    "\n";
        }

    }
}
