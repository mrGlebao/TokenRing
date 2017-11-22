package strategy.node;

import entities.Node;
import entities.dto.Frame;
import entities.dto.Message;

/**
 * Default token ring strategy implementation, according to wikipedia.
 */
public class VanillaTokenRingStrategy extends AbstractNodeStrategy {

    public VanillaTokenRingStrategy(Node node) {
        super(node);
    }

    @Override
    protected void sendNewMessage(Frame frame) {
        Message mess = node.getOperator().getMessage();
        frame.setMessage(mess);
        frame.setTokenFlag(false);
        node.sendMessage(frame);
    }

    @Override
    protected void frameHasReachedSender(Frame frame) {
        frame.setMessage(null);
        frame.setTokenFlag(true);
        node.sendMessage(frame);
    }

    @Override
    protected void frameHasReachedAddressee(Frame frame) {
        node.saveMessage(frame.copy());
        frame.setTokenFlag(true);
        node.sendMessage(frame);
    }

}
