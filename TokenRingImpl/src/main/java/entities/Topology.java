package entities;

import entities.dto.Frame;
import throwables.UnexpectedAddresseeException;

import java.util.ArrayList;
import java.util.List;

public class Topology {

    private List<Node> topology = new ArrayList<>();

    public Topology(int num) {
        this.createRingTopology(num);
    }

    private void add(Node node) {
        if (topology.size() > 0) {
            Node t = topology.get(topology.size() - 1);
            t.setNext(node);
            node.setNext(topology.get(0));
        }
        topology.add(node);
    }

    public void createRingTopology(int number) {
        for (int i = 0; i < number; i++) {
            add(new Node(i));
        }
    }

    public void start() {
        for (Node t : topology) {
            t.start();
        }

    }

    public void setTokenTo(int i) {
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

    public void setTokenToRandom() {
        setTokenTo((int) (Math.random() * (topology.size() - 1)));
    }

    public void stop() {
        for (Node t : topology) {
            t.interrupt();
        }
    }

}
