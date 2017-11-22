package strategy.operator;

import entities.Operator;

public abstract class OperatorWaitStrategy implements OperatorStrategy {

    private final Operator op;

    protected OperatorWaitStrategy(Operator op) {
        this.op = op;
    }

    @Override
    public void apply() {
        try {
            Thread.sleep(sleepTime());
        } catch (InterruptedException e) {
            op.interrupt();
        }
    }

    public abstract long sleepTime();
}
