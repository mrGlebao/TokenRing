package strategy;

import entities.Node;
import entities.Operator;
import entities.dto.Frame;
import entities.dto.Message;

import static utils.Utils.log;

public class VanillaTokenRingStrategy extends AbstractNodeStrategy {

    private final Node node;

    public VanillaTokenRingStrategy(Node node) {
        this.node = node;
    }

    @Override
    public void operateEmptyToken(Frame frame) {
        Operator op = node.getOperator();
        log("Node " + node.getNodeId() + " received empty token");
        if (op.hasMessageToSend()) {
            // Prepare message and send
            Message mess = op.getMessage();
            log("Operator sent message from " + mess.from() + " to " + mess.to());
            frame.setMessage(mess);
            frame.setTokenFlag(false);
            node.sendMessage(frame);
        } else {
            // Just push token forward
            log("operator " + op.getOperatorId() + " is silent. Node " + node.getNodeId() + " sent empty token");
            node.sendMessage(frame);
        }
    }

    @Override
    public void operateForeignMessage(Frame frame) {
        Message mess = frame.getMessage();
        log(node.getNodeId() + " received message " + mess + " from " + mess.from() + " to " + mess.to());
        if (mess.from() == node.getNodeId()) {
            // Send empty token instead
            log("Returned home!");
            node.sendMessage(Frame.createToken());
        } else if (mess.to() == node.getNodeId()) {
            // Collect message
            log("Went to addressee!");
            node.saveMessage(frame);
            frame.setTokenFlag(false);
            node.sendMessage(frame);
        } else {
            // Just push message forward
            log("Not mine!");
            node.sendMessage(frame);
        }
    }
}
