package strategy;

import entities.Node;
import entities.Operator;
import entities.dto.Frame;
import entities.dto.Message;

import static utils.Utils.log;

public class VanillaTokenRingStrategy extends AbstractNodeStrategy {

    public VanillaTokenRingStrategy(Node node) {
        super(node);
    }

    @Override
    void sendNewMessage(Frame frame) {
        Operator op = node.getOperator();
        Message mess = op.getMessage();
        log("Operator "+op.getOperatorId()+" sent message from " + mess.from() + " to " + mess.to());
        frame.setMessage(mess);
        frame.setTokenFlag(false);
        node.sendMessage(frame);
    }

    @Override
    void frameHasReachedSender(Frame frame) {
        // Send empty token instead
        log("Returned home!");
        node.sendMessage(Frame.createToken());
    }

    @Override
    void frameHasReachedAddressee(Frame frame) {
    // Collect message
        log("Went to addressee!");
        node.saveMessage(frame);
        frame.setTokenFlag(false);
        node.sendMessage(frame);
    }

}
