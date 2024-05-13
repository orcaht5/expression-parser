package expression;

public class Divide extends BinaryOperation {
    public Divide(final PriorityOperation expression1, final PriorityOperation expression2) {
        super(expression1, expression2, 60, false, false);
    }

    @Override
    public String getOperationDesignation() {
        return "/";
    }

    @Override
    public IntegerOperation getIntegerOperation() {
        return (int x, int y) -> x / y;
    }

    @Override
    public DoubleOperation getDoubleOperation() {
        return (double x, double y) -> x / y;
    }
}
