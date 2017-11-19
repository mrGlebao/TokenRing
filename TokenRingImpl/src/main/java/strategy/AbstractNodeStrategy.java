package strategy;

import entities.dto.Frame;

/**
 * Abstract class which defines node behavior.
 * Superclass for each node strategy implementation.
 */
public abstract class AbstractNodeStrategy implements Strategy {

    abstract void operateEmptyToken(Frame frame);

    abstract void operateForeignMessage(Frame frame);

    @Override
    public final void apply(Frame frame) {
        if (frame.isEmptyToken()) {
            operateEmptyToken(frame);
        } else {
            operateForeignMessage(frame);
        }
    }

}
