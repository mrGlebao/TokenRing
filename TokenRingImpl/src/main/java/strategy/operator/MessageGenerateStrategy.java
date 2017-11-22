package strategy.operator;

import conf.Settings;
import entities.TopologyOverseer;
import entities.dto.Message;

import static utils.Utils.log;

public abstract class MessageGenerateStrategy implements OperatorStrategy {

    private final double verbose;
    private final int operatorId;

    public MessageGenerateStrategy(double verbose, int operatorId) {
        this.operatorId = operatorId;
        this.verbose = Settings.RUSH_MODE ? 1 : verbose;
    }

    @Override
    public void apply() {
        if (this.verbose > Math.random()/3) {
            log("Operator ID" + operatorId + " GENERATES MESSAGE!");
            prepareMessage();
        }
    }

    private int generateAddresate() {
        while (true) {
            int to_temp = (int) ((Settings.TOPOLOGY_SIZE - 1) * Math.random());
            if (to_temp != this.operatorId)
                return to_temp;
        }
    }

    private void prepareMessage() {
        int to = generateAddresate();
        String message = "from op" + operatorId + " to op" + to + " message: you are stinky!";
        TopologyOverseer.incrementGenerated();
        readyToSend(new Message(operatorId, to, message));
    }

    protected abstract void readyToSend(Message message);
}