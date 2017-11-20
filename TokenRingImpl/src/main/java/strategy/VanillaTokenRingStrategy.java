package strategy;

import entities.MessagesOverseer;
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
    protected void sendNewMessage(Frame frame) {
        Operator op = node.getOperator();
        Message mess = op.getMessage();
        log("Operator "+op.getOperatorId()+" sent message from " + mess.from() + " to " + mess.to());
        frame.setMessage(mess);
        frame.setTokenFlag(false);
        MessagesOverseer.incrementSent();
        node.sendMessage(frame);
    }

    @Override
    protected void frameHasReachedSender(Frame frame) {
        node.sendMessage(Frame.createToken());
    }

    @Override
    protected void frameHasReachedAddressee(Frame frame) {
        node.saveMessage(frame);
        frame.setTokenFlag(false);
        node.sendMessage(frame);
    }

}
