package expression;

public class Clear extends BinaryOperation {
    public Clear(final PriorityOperation expression1, final PriorityOperation expression2) {
        super(expression1, expression2, 40, false, true);
    }

    @Override
    public String getOperationDesignation() {
        return "clear";
    }

    @Override
    public IntegerOperation getIntegerOperation() {
        return ((x, y) -> (x ^ (1 << y)) & x);
    }

    @Override
    public DoubleOperation getDoubleOperation() {
        throw new IllegalArgumentException("Can't clear bit with double index");
    }
}
