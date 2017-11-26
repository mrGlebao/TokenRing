import conf.Settings;
import entities.Topology;
import entities.TopologyOverseer;

public class Main {

    private static void sendTokens(Topology t, int i) {
        int temp = 0;
        while(temp < i) {
            t.askOperatorTo().sendTokenTo(temp);
            temp++;
        }
    }

    private static void goGoGo(int maxTopologySize, int tokensNum) {
        for(int i = 3; i < maxTopologySize; i+=2) {
            if(tokensNum > i) {
                System.out.println("Continue");
                continue;
            }
            System.out.println("Top "+i);
            System.out.println("Tok "+tokensNum);

            Settings.TOPOLOGY_SIZE = i;
            Settings.TOKENS_SENT = tokensNum;

            Topology top = Topology.createRing(Settings.TOPOLOGY_SIZE);
            top.start();
            sendTokens(top, Settings.TOKENS_SENT );
            while (Settings.MESSAGES_TO_RECEIVE > TopologyOverseer.numberOfMessagesReceived()) {
            }
            top.stop();
            TopologyOverseer.printAllReceivedStamps();
            TopologyOverseer.clear();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i < 30; i++) {
            goGoGo(30, i);
        }
    }
}
