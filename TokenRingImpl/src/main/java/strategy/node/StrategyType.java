package strategy.node;

import entities.Node;

/**
 * Type of node behavior strategy
 *
 * @See {@link NodeStrategy}
 */
public enum StrategyType {
    DEFAULT, EARLY_RELEASE;

    public NodeStrategy getNodeStrategy(Node node) {
        if (this == EARLY_RELEASE) {
            return new EarlyTokenReleaseStrategy(node);
        } else return new VanillaTokenRingStrategy(node);
    }

}
