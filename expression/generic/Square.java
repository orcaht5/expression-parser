package expression.generic;

public class Square<T> implements PriorityExpression<T> {
    private final PriorityExpression<T> expression;
    private final GenericOperations<T> operator;

    public Square(final PriorityExpression<T> expression, final GenericOperations<T> operator) {
        this.expression = expression;
        this.operator = operator;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return operator.square(expression.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "square(" + expression.toString() + ")";
    }
}
