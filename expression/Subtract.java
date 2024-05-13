package expression;

public class Subtract extends BinaryOperation {
    public Subtract(final PriorityOperation expression1, final PriorityOperation expression2) {
        super(expression1, expression2, 50, false, true);
    }

    @Override
    public String getOperationDesignation() {
        return "-";
    }

    @Override
    public IntegerOperation getIntegerOperation() {
        return (int x, int y) -> x - y;
    }

    @Override
    public DoubleOperation getDoubleOperation() {
        return (double x, double y) -> x - y;
    }
}
