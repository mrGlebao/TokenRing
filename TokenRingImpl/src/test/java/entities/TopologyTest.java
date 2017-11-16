package entities;

import conf.Settings;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TopologyTest {

    private Topology top;
    private int size;
    private Iterable<Node> topInside;

    @Before
    public void before() {
        size = appropriateSize(Settings.TOPOLOGY_SIZE);
        top = Topology.createRing(size);
        try {
            Field f = Topology.class.getDeclaredField("topology");
            f.setAccessible(true);
            topInside = (Iterable<Node>) f.get(top);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // ToDo: what if maxSize < threshold ?
    private int appropriateSize(int maxSize) {
        int size = 0;
        while (size < 1) {
            size = (int) (Math.random() * maxSize);
        }
        return size;
    }

    private Node[] collectIterator(Iterable<Node> iterable) {
        Node[] result = new Node[size];
        Iterator<Node> it = iterable.iterator();
        for (int i = 0; i < size; i++) {
            result[i] = it.next();
        }
        return result;
    }

    @Test
    public void CheckTopologyInnerSize() {
        int sizeActual = 0;
        for (Node e : topInside) {
            sizeActual++;
        }
        assertTrue("Inner size is " + sizeActual + ", expected size is " + size, sizeActual == size);
    }


    @Test
    public void CheckSequenceInTheMiddle() throws NoSuchFieldException, IllegalAccessException {
        int number = 0;
        Node nodesAsArray[] = collectIterator(topInside);
        for (int i = 0; i < nodesAsArray.length - 1; i++) {
            Node previous = nodesAsArray[i];
            Node next = nodesAsArray[i + 1];
            Field fieldNext = Node.class.getDeclaredField("next");
            fieldNext.setAccessible(true);
            Node actualNext = (Node) fieldNext.get(previous);
            assertEquals("For node " + previous + " actual next is " + actualNext + ", but expected next is " + next, next, actualNext);
        }
    }

    @Test
    public void CheckEdgeSequence() throws NoSuchFieldException, IllegalAccessException {
        Node nodesAsArray[] = collectIterator(topInside);
        if (nodesAsArray.length > 0) {
            Node previous = nodesAsArray[nodesAsArray.length - 1];
            Node next = nodesAsArray[0];
            Field fieldNext = Node.class.getDeclaredField("next");
            fieldNext.setAccessible(true);
            Node actualNext = (Node) fieldNext.get(previous);
            assertEquals("For node " + previous + " actual next is " + actualNext + ", but expected next is " + next, next, actualNext);
        }
    }

}
