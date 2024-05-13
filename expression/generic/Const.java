package expression.generic;

public class Const<T> implements PriorityExpression<T> {
    private final int value;
    private final GenericOperations<T> operator;

    public Const(final int value, final GenericOperations<T> operator) {
        this.value = value;
        this.operator = operator;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return operator.getValue(value);
    }
}
