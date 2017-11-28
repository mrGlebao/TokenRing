package strategy.operator;

import conf.Settings;
import entities.TopologyOverseer;
import entities.dto.Message;

import static utils.Utils.log;

/**
 * Operator strategy for message generation.
 *
 * @See Operator
 */
public abstract class MessageGenerateStrategy implements OperatorStrategy {

    private final double verbose;
    private final int operatorId;

    public MessageGenerateStrategy(double verbose, int operatorId) {
        this.operatorId = operatorId;
        this.verbose = Settings.RUSH_MODE ? 1 : verbose;
    }

    @Override
    public void apply() {
        if (this.verbose > Math.random()) {
            // Won a dice throw
            log("Operator ID" + operatorId + " GENERATES MESSAGE!");
            prepareMessage();
        }
    }

    private int generateAddresate() {
        while (true) {
            int to_temp = (int) ((TopologyOverseer.getActualTopologySize()) * Math.random());
            if (to_temp != this.operatorId)
                return to_temp;
        }
    }

    private void prepareMessage() {
        int to = generateAddresate();
        String message = "from op" + operatorId + " to op" + to + " message: you are stinky!";
        TopologyOverseer.incrementGenerated();
        Message mess = new Message(operatorId, to, message);
        mess.setGenerated();
        readyToSend(mess);
    }

    /**
     * What to do when message was successfully generated.
     */
    protected abstract void readyToSend(Message message);
}