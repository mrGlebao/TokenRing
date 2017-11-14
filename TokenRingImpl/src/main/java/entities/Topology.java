package entities;

import entities.dto.Frame;
import throwables.UnexpectedAddresseeException;

import java.util.ArrayList;
import java.util.List;

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
        Topology top = new Topology();
        for (int i = 0; i < number; i++) {
            top.askRingConstructor().append(new Node(i));
        }
        return top;
    }


    public void start() {
        for (Node t : topology) {
            t.start();
        }

    }

    public void stop() {
        for (Node t : topology) {
            t.interrupt();
        }
    }

    // ToDo: public if we will need to append nodes in runtime.
    private RingConstructor askRingConstructor() {
        return constructor;
    }

    public TopologyOperator askOperator() {
        return operator;
    }

    /**
     * Inner class to build topology
     */
    public class RingConstructor {

        private RingConstructor(){}

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

        private TopologyOperator(){}

        public void sendTokenTo(int i) {
            if (i > topology.size() - 1) {
                try {
                    throw new UnexpectedAddresseeException(i);
                } catch (UnexpectedAddresseeException e) {
                    e.printStackTrace();
                    return;
                }
            }
            topology.get(i).sendMessage(Frame.createToken());
        }

        public void sendTokenToRandom() {
            sendTokenTo((int) (Math.random() * (topology.size() - 1)));
        }

    }

}
