package expression.generic;

public interface PriorityExpression<T> {
    T evaluate(int x, int y, int z);
}