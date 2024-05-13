package expression;

public class Set extends BinaryOperation {
    public Set(final PriorityOperation expression1, final PriorityOperation expression2) {
        super(expression1, expression2, 40, false, true);
    }

    @Override
    public String getOperationDesignation() {
        return "set";
    }

    @Override
    public IntegerOperation getIntegerOperation() {
        return ((x, y) -> (x | (1 << y)));
    }

    @Override
    public DoubleOperation getDoubleOperation() {
        throw new IllegalArgumentException("Can't set bit with double index");
    }
}
