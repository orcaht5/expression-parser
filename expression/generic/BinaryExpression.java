package expression.generic;

public class BinaryExpression<T> implements PriorityExpression<T> {
    private final BinaryOperation<T> operation;
    private final GenericOperations<T> operator;
    private final PriorityExpression<T> expression1;
    private final PriorityExpression<T> expression2;

    public BinaryExpression(
            BinaryOperation<T> operation,
            GenericOperations<T> operator,
            PriorityExpression<T> expression1,
            PriorityExpression<T> expression2
    ) {
        this.operation = operation;
        this.operator = operator;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return operation.getBinaryOperation(
                expression1.evaluate(x, y, z),
                expression2.evaluate(x, y, z),
                operator
        );
    }
}
