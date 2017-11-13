public class Main {

    public static void main(String[] args) throws InterruptedException {
        Topology top = new Topology(Settings.TOPOLOGY_SIZE);
        top.start();
        Node tok = top.topology.get(0);
//        tok.sendPackage(17, "Ass1");
//        tok.sendPackage(18, "Ass2");
//        tok.sendPackage(19, "Ass3");
        Thread.sleep(Settings.MAIN_SLEEP_DEFAULT);
        top.stop();
    }
}
