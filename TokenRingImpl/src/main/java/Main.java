import conf.Settings;
import entities.Topology;
import entities.TopologyOverseer;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Topology top = Topology.createRing(Settings.TOPOLOGY_SIZE);
        top.start();
        top.askOperatorTo().sendTokenTo(Settings.TOPOLOGY_SIZE / 2);
        while (Settings.MESSAGES_TO_RECEIVE > TopologyOverseer.numberOfMessagesReceived()) {
        }
        top.stop();
        TopologyOverseer.printAllReceivedStamps();
    }
}
