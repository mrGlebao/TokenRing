import conf.Settings;
import entities.Topology;
import entities.TopologyOverseer;
import entities.dto.Message;
import throwables.TopologySizeException;
import timestamp_writer.TimestampWriter;

import java.util.List;

public class Main {

    private static void sendTokens(Topology t, int i) throws TopologySizeException {
        int temp = 0;
        if (t.size() < i) {
            throw new TopologySizeException("Topology size is " + t.size() + ", but try to sent " + i + " empty tokens!");

        }
        while (temp < i) {
            t.askOperatorTo().sendTokenTo(temp);
            temp++;
        }
    }

    private static void mainIteration(int topologySize, int tokensNum) {
        Topology top = Topology.createRing(topologySize);
        try {
            sendTokens(top, tokensNum);
        } catch (TopologySizeException e) {
            e.printStackTrace();
            return;
        }
        top.start();
        System.out.println("Topology size: " + topologySize);
        System.out.println("Tokens number: " + tokensNum);
        while (Settings.MESSAGES_TO_RECEIVE > TopologyOverseer.numberOfMessagesReceived()) {
        }
        top.stop();
        List<Message.Timestamps> stamps = TopologyOverseer.getAllReceivedStamps();
        TopologyOverseer.clear();
        TimestampWriter.write(topologySize, tokensNum, stamps);
    }

    private static void goGoGo() throws TopologySizeException {
        if (Settings.TOPOLOGY_SIZE_MIN < 2) {
            throw new TopologySizeException("Minimal topology size is too small: " + Settings.TOPOLOGY_SIZE_MIN + ". Must be at least 2");
        }
        if (Settings.TOPOLOGY_SIZE_MIN > Settings.TOPOLOGY_SIZE_MAX) {
            throw new TopologySizeException("Minimal topology size " + Settings.TOPOLOGY_SIZE_MIN + " is bigger " +
                    "than maximal topology size " + Settings.TOPOLOGY_SIZE_MAX);
        }
        if (Settings.TOPOLOGY_SIZE_MIN == Settings.TOPOLOGY_SIZE_MAX) {
            System.out.println("Minimal topology size " + Settings.TOPOLOGY_SIZE_MIN + " is equal to maximal" +
                    " topology size. Topology size is fixed during measurements.");
        }
        if (Settings.SERIES_MODE) {
            // Topology number loop
            for (int i = Settings.TOPOLOGY_SIZE_MIN; i <= Settings.TOPOLOGY_SIZE_MAX; i += Settings.TOPOLOGY_SIZE_STEP) {
                //Tokens number loop
                for (int j = 1; j <= i; j += Settings.TOKENS_SENT_STEP) {
                    mainIteration(i, j);
                }
            }
        } else {
            mainIteration(Settings.TOPOLOGY_SIZE, Settings.TOKENS_SENT);
        }
    }


    public static void main(String[] args) {
        try {
            goGoGo();
        } catch (TopologySizeException e) {
            e.printStackTrace();
        }
    }
}
