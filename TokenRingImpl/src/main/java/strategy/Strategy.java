package strategy;

import entities.dto.Frame;

/**
 * Interface for behavior strategies.
 *
 */
public interface Strategy {

    void apply(Frame frame);
}
