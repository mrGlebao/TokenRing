package entities;

import conf.Settings;
import entities.dto.Frame;

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
        topology.get((int)(Math.random() * (Settings.TOPOLOGY_SIZE-1))).sendMessage(Frame.createToken());
    }

    public void stop() {
        for (Node t : topology) {
            t.interrupt();
        }
    }

}
