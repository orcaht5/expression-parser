package expression.generic;

public class Variable<T> implements PriorityExpression<T> {
    private final String variable;
    private final GenericOperations<T> operator;

    public Variable(final String variable, final GenericOperations<T> operator) {
        this.variable = variable;
        this.operator = operator;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        switch (variable) {
            case "x" -> {
                return operator.getValue(x);
            }
            case "y" -> {
                return operator.getValue(y);
            }
            case "z" -> {
                return operator.getValue(z);
            }
            default -> throw new IllegalArgumentException("Only three variables are allowed: x, y, z");
        }
    }

    @Override
    public String toString() {
        return variable;
    }
}
