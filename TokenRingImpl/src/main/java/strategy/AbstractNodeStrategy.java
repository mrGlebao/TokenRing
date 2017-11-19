package strategy;

import entities.Node;
import entities.Operator;
import entities.dto.Frame;
import entities.dto.Message;

import static utils.Utils.log;

/**
 * Abstract class which defines node behavior.
 * Superclass for each node strategy implementation.
 */
public abstract class AbstractNodeStrategy implements Strategy {

    protected final Node node;

    AbstractNodeStrategy(Node node) {
        this.node = node;
    }

    public void operateEmptyToken(Frame frame) {
        Operator op = node.getOperator();
        log("Node " + node.getNodeId() + " received empty token");
        if (op.hasMessageToSend()) {
            sendNewMessage(frame);
        } else {
            pushTokenForward(frame);
        }
    }

    private void pushTokenForward(Frame frame) {
        log("Node " + node.getNodeId() + " operator is silent. Pushes empty token forward.");
        node.sendMessage(frame);
    }


    public void operateFrame(Frame frame) {
        Message mess = frame.getMessage();
        log(node.getNodeId() + " received message " + mess );
        if (mess.from() == node.getNodeId()) {
            frameHasReachedSender(frame);
        } else if (mess.to() == node.getNodeId()) {
            frameHasReachedAddressee(frame);
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

    abstract void frameHasReachedSender(Frame frame);

    abstract void frameHasReachedAddressee(Frame frame);

    abstract void sendNewMessage(Frame frame);

}