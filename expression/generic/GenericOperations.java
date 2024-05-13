package expression.generic;

public interface GenericOperations<T> {
    T add(T number1, T number2);

    T subtract(T number1, T number2);

    T divide(T number1, T number2);

    T multiply(T number1, T number2);

    T getValue(Number number);

    T negate(T number);

    T abs(T number);

    T mod(T number1, T number2);

    T square(T number);
}
