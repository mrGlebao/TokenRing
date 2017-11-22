package entities;

import conf.Settings;
import entities.dto.Message;
import strategy.operator.MessageGenerateStrategy;
import strategy.operator.OperatorStrategy;
import strategy.operator.OperatorWaitStrategy;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static utils.Utils.log;

/**
 * A class which represents the operator - an entity,
 * which generates messages with random frequency dependent on its verbosity.
 * Each node has its own operator.
 */
public class Operator extends Thread {

    private final Queue<Message> messagesToSend;

    private final int nodeId;
    private final double verbose;
    private OperatorStrategy messageGenerationStrategy;

    private OperatorStrategy waitStrategy;

    public Operator(double verbose, int nodeId) {
        this.nodeId = nodeId;
        this.verbose = verbose;
        this.messagesToSend = new ConcurrentLinkedQueue<>();
        messageGenerationStrategy = new MessageGenerateStrategy(verbose, nodeId) {
            @Override
            protected void readyToSend(Message message) {
                messagesToSend.add(message);
            }
        };
        waitStrategy = new OperatorWaitStrategy(this) {
            @Override
            public long sleepTime() {
                return Settings.OPERATOR_SLEEP_DEFAULT;
            }
        };
    }


    public boolean hasMessageToSend() {
        return !messagesToSend.isEmpty();
    }

    public Message getMessage() {
        return messagesToSend.poll();
    }

    public Message peekMessage() {
        return messagesToSend.peek();
    }

    public int getOperatorId() {
        return this.nodeId;
    }

    @Override
    public void run() {
        log("Operator ID" + nodeId + " and verbose " + verbose + " has awakened!");
        while (!isInterrupted() && TopologyOverseer.topologyIsAlive()) {
            messageGenerationStrategy.apply();
            waitStrategy.apply();
        }
    }

}
