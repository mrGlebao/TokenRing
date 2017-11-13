import entities.Topology;
import conf.Settings;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Topology top = new Topology(Settings.TOPOLOGY_SIZE);
        top.start();
        Thread.sleep(Settings.MAIN_SLEEP_DEFAULT);
        top.stop();
    }
}
