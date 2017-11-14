package throwables;

public class UnexpectedAddresseeException extends Throwable {

    public UnexpectedAddresseeException(int addressee) {
        super(" Topology has less than " + addressee + " nodes!");
    }

}
