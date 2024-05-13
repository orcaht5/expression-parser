package expression;

public class Multiply extends BinaryOperation {
    public Multiply(final PriorityOperation expression1, final PriorityOperation expression2) {
        super(expression1, expression2, 60, true, true);
    }

    @Override
    public String getOperationDesignation() {
        return "*";
    }

    public IntegerOperation getIntegerOperation() {
        return (int x, int y) -> x * y;
    }

    @Override
    public DoubleOperation getDoubleOperation() {
        return (double x, double y) -> x * y;
    }
}
