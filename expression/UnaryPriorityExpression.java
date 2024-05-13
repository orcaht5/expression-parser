package expression;

public interface UnaryPriorityExpression extends PriorityOperation {
    @Override
    default int getPriority() {
        return 100;
    }

    @Override
    default boolean isAssociative() {
        return true;
    }

    @Override
    default boolean isMatching() {
        return true;
    }
}
