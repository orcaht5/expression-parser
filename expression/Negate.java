package expression;

import java.util.Objects;

public class Negate implements UnaryPriorityExpression {
    final PriorityOperation expression;

    public Negate(final PriorityOperation expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -expression.evaluate(x, y, z);
    }

    @Override
    public boolean isAssociative() {
        return true;
    }

    @Override
    public boolean isMatching() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Negate negate = (Negate) o;
        return Objects.equals(expression, negate.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, expression.getClass());
    }

    @Override
    public String toString() {
        return "-(" + expression.toString() + ")";
    }

    @Override
    public String toMiniString() {
        return getPriority() > expression.getPriority() ? "-(" + expression.toMiniString() + ")" : "- " + expression.toMiniString();
    }
}
