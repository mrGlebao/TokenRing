package strategy;

import entities.dto.Frame;

/**
 * Interface for various behavior strategies.
 */
public interface Strategy {

    void apply(Frame frame);
}
