package expression;

import java.util.Objects;

public abstract class BinaryOperation implements PriorityBinaryOperation {
    private final PriorityOperation expression1;
    private final PriorityOperation expression2;
    private final int priority;
    private final boolean associativity;
    private final boolean matching;

    public BinaryOperation(
            final PriorityOperation expression1,
            final PriorityOperation expression2,
            int priority,
            boolean associativity,
            boolean matching) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.priority = priority;
        this.associativity = associativity;
        this.matching = matching;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean isAssociative() {
        return associativity;
    }

    @Override
    public boolean isMatching() {
        return matching;
    }

    @Override
    public String toString() {
        return '(' + expression1.toString() + ' ' + getOperationDesignation() + ' ' + expression2.toString() + ')';
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression1, expression2, this.getClass());
    }

    @Override
    public String toMiniString() {
        return childToMiniString(expression1, false) + ' ' +
                getOperationDesignation() + ' ' + childToMiniString(expression2, true);
    }

    private String childToMiniString(PriorityOperation expression, boolean second) {
        StringBuilder expr = new StringBuilder(expression.toMiniString());
        if (expression.getPriority() < this.getPriority() ||
                second && (!this.isAssociative() || !expression.isMatching())
                        && expression.getPriority() == this.getPriority()) {
            expr.insert(0, '(');
            expr.append(')');
        }
        return expr.toString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getIntegerOperation().doOperation(expression1.evaluate(x, y, z), expression2.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            BinaryOperation anotherExpression = (BinaryOperation) obj;
            return expression1.equals(anotherExpression.expression1)
                    && expression2.equals(anotherExpression.expression2);
        }
        return false;
    }
}
