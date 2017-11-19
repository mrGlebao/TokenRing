package strategy;

import entities.Node;
import entities.dto.Frame;

import static utils.Utils.log;

public class EarlyTokenReleaseStrategy extends VanillaTokenRingStrategy {

    private VanillaTokenRingStrategy strategy;


    public EarlyTokenReleaseStrategy(Node node) {
        super(node);
    }

    @Override
    public void sendNewMessage(Frame frame) {
        super.sendNewMessage(frame);
        log("Early release: " + node + " sent token!");
        node.sendMessage(Frame.createToken());
    }

}
