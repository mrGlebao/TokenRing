import entities.Topology;
import conf.Settings;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Topology top = Topology.createRing(Settings.TOPOLOGY_SIZE);
        top.start();
        top.askOperator().sendTokenTo(Settings.TOPOLOGY_SIZE / 2);
        Thread.sleep(Settings.MAIN_SLEEP_DEFAULT);
        top.stop();
    }
}
