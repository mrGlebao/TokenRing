package strategy.node;

import entities.dto.Frame;

/**
 * Interface for various node behavior strategies.
 * Created to separate node behavior logic from data.
 */
public interface NodeStrategy {

    void apply(Frame frame);
}
