package strategy.node;

import entities.Node;
import entities.Operator;
import entities.TopologyOverseer;
import entities.dto.Frame;
import entities.dto.Message;

import static utils.Utils.log;

/**
 * Abstract class which defines node behavior.
 * Template for each node strategy implementation.
 * Template for each node strategy implementation.
 * Created to separate the logic from the data.
 */
public abstract class AbstractNodeStrategy implements NodeStrategy {

    protected final Node node;

    AbstractNodeStrategy(Node node) {
        this.node = node;
    }

    private void operateEmptyToken(Frame frame) {
        Operator op = node.getOperator();
        log("Node " + node.getNodeId() + " received empty token");
        if (op.hasMessageToSend()) {
            sendNewMessageTemplate(frame);
        } else {
            pushTokenForward(frame);
        }
    }

    private void pushTokenForward(Frame frame) {
        log("Node " + node.getNodeId() + " operator is silent. Pushes empty token forward.");
        node.sendMessage(frame);
    }


    private void operateFrame(Frame frame) {
        Message mess = frame.getMessage();
        log(node.getNodeId() + " received message " + mess);
        if (mess.from() == node.getNodeId()) {
            frameHasReachedSenderTemplate(frame);
        } else if (mess.to() == node.getNodeId()) {
            frameHasReachedAddresseeTemplate(frame);
        } else {
            frameHasReachedUsualNode(frame);
        }
    }

    private void frameHasReachedUsualNode(Frame frame) {
        // Just push message forward
        log("Not mine!");
        node.sendMessage(frame);
    }


    @Override
    public final void apply(Frame frame) {
        if (frame.isEmptyToken()) {
            operateEmptyToken(frame);
        } else {
            operateFrame(frame);
        }
    }

    private void frameHasReachedSenderTemplate(Frame frame) {
        // Send empty token instead
        log("Returned home!");
        frameHasReachedSender(frame);
        TopologyOverseer.incrementReturned();
    }

    protected abstract void frameHasReachedSender(Frame frame);

    private void frameHasReachedAddresseeTemplate(Frame frame) {
        // Collect message
        log("Went to addressee!");
        frameHasReachedAddressee(frame);
        TopologyOverseer.incrementReceived();
    }

    protected abstract void frameHasReachedAddressee(Frame frame);

    private void sendNewMessageTemplate(Frame frame) {
        Operator op = node.getOperator();
        Message mess = op.peekMessage();
        log("Operator " + op.getOperatorId() + " sent message from " + mess.from() + " to " + mess.to());
        TopologyOverseer.incrementSent();
        sendNewMessage(frame);
    }

    protected abstract void sendNewMessage(Frame frame);

}