package expression;

import java.util.Objects;

public class Count implements UnaryPriorityExpression {
    final PriorityOperation expression;

    public Count(final PriorityOperation expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.bitCount(expression.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Count negate = (Count) o;
        return Objects.equals(expression, negate.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, expression.getClass());
    }

    @Override
    public String toString() {
        return "count(" + expression.toString() + ")";
    }

    @Override
    public String toMiniString() {
        return getPriority() > expression.getPriority() ? "count(" + expression.toMiniString() + ")" : "count " + expression.toMiniString();
    }
}
