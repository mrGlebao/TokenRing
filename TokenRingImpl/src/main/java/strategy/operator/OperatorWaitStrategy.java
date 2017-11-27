package strategy.operator;

import conf.Settings;
import entities.Operator;
import entities.TopologyOverseer;

/**
 * Operator strategy for waiting
 *
 * @See Operator
 */
public abstract class OperatorWaitStrategy implements OperatorStrategy {

    private final Operator op;

    protected OperatorWaitStrategy(Operator op) {
        this.op = op;
    }

    @Override
    public void apply() {
        if (!Settings.RUSH_MODE) {
            try {
                Thread.sleep(sleepTimeMillis(), sleepTimeNanos());
            } catch (InterruptedException e) {
                if (!TopologyOverseer.topologyIsAlive()) {
                    op.interrupt();
                }
            }
        }
    }

    public abstract int sleepTimeMillis();

    public abstract int sleepTimeNanos();

}
