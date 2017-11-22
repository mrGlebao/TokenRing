package strategy.node;

import entities.Node;

public enum StrategyType {
    DEFAULT, EARLY_RELEASE;

    public static AbstractNodeStrategy getNodeStrategy(StrategyType type, Node node) {
        if (type == EARLY_RELEASE) {
            return new EarlyTokenReleaseStrategy(node);
        } else return new VanillaTokenRingStrategy(node);
    }

}
