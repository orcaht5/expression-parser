package expression;

public class Pow10 implements UnaryPriorityExpression {
    final PriorityOperation expression;

    public Pow10(final PriorityOperation expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int)Math.pow(10, expression.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "pow10(" + expression.toString() + ")";
    }

    @Override
    public String toMiniString() {
        return getPriority() > expression.getPriority() ? "pow10(" + expression.toMiniString() + ")" : "pow10 " + expression.toMiniString();
    }
}
