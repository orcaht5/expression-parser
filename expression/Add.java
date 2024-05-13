package expression;

public class Add extends BinaryOperation {
    public Add(final PriorityOperation expression1, final PriorityOperation expression2) {
        super(expression1, expression2, 50, true, true);
    }

    @Override
    public String getOperationDesignation() {
        return "+";
    }

    @Override
    public IntegerOperation getIntegerOperation() {
        return Integer::sum;
    }

    @Override
    public DoubleOperation getDoubleOperation() {
        return Double::sum;
    }
}
