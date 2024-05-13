package expression;

public interface PriorityOperation extends TripleExpression {
    int getPriority();

    boolean isAssociative();

    boolean isMatching();
}
