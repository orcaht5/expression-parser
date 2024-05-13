package expression;

public interface PriorityBinaryOperation extends PriorityOperation {
    String getOperationDesignation();

    IntegerOperation getIntegerOperation();

    DoubleOperation getDoubleOperation();
}
