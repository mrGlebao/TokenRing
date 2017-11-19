import conf.Settings;
import entities.ReceivedMessagesOverseer;
import entities.Topology;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Topology top = Topology.createRing(Settings.TOPOLOGY_SIZE);
        top.start();
        top.askOperator().sendTokenTo(Settings.TOPOLOGY_SIZE / 2);
        while (Settings.MESSAGES_TO_RECEIVE >= ReceivedMessagesOverseer.numberOfMessagesReceived()) {
        }
        ReceivedMessagesOverseer.printAllReceivedMessages();
        top.stop();
    }
}
