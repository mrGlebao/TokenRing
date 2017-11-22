package strategy.node;

import entities.dto.Frame;

/**
 * Interface for various node behavior strategies.
 */
public interface NodeStrategy {

    void apply(Frame frame);
}
