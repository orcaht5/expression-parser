package expression;

public class Log10 implements UnaryPriorityExpression {
    final PriorityOperation expression;

    public Log10(final PriorityOperation expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int)Math.log10(expression.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "log10(" + expression.toString() + ")";
    }

    @Override
    public String toMiniString() {
        return getPriority() > expression.getPriority() ? "log10(" + expression.toMiniString() + ")" : "log10 " + expression.toMiniString();
    }
}
