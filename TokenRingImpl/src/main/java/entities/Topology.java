package entities;

import conf.Settings;
import entities.dto.Frame;
import strategy.node.StrategyType;

import java.util.ArrayList;
import java.util.List;

import static utils.Utils.log;

/**
 * Class to represent node topology, i.e. TokenRing
 */
public class Topology {

    private RingConstructor constructor;
    private TopologyOperator operator;
    private List<Node> topology = new ArrayList<>();

    private Topology() {
        this.constructor = new RingConstructor();
        this.operator = new TopologyOperator();
    }

    public static Topology createRing(int number) {
        return createRing(number, Settings.EARLY_RELEASE ? StrategyType.EARLY_RELEASE : StrategyType.DEFAULT);
    }

    public static Topology createRing(int number, StrategyType strategy) {
        Topology top = new Topology();
        for (int i = 0; i < number; i++) {
            top.askRingConstructor().append(new Node(i, strategy));
        }
        return top;
    }


    public void start() {
        TopologyOverseer.setTopologyIsAliveFlag(true);
        for (Node t : topology) {
            t.start();
        }

    }

    public void stop() {
        TopologyOverseer.setTopologyIsAliveFlag(false);
        for (Node t : topology) {
            t.interrupt();
        }
    }

    private RingConstructor askRingConstructor() {
        return constructor;
    }

    public TopologyOperator askOperatorTo() {
        return operator;
    }

    /**
     * Inner class to build topology
     */
    public class RingConstructor {

        private RingConstructor() {
        }

        void append(Node node) {
            if (topology.size() > 0) {
                Node t = topology.get(topology.size() - 1);
                t.setNext(node);
                node.setNext(topology.get(0));
            }
            topology.add(node);
        }
    }

    /**
     * Inner class to manage empty token creation.
     */
    public class TopologyOperator {

        private TopologyOperator() {
        }

        public void sendTokenTo(int i) {
            if (i > topology.size() - 1) {
                log(" Topology has less than " + i + " nodes!");
                return;
            }
            topology.get(i).sendMessage(Frame.createToken());
        }

        public void sendTokenToRandom() {
            sendTokenTo((int) (Math.random() * (topology.size() - 1)));
        }

    }

}
