package expression.generic;

import expression.exceptions.DivisionByZero;

public class UnsignedIntOperations implements GenericOperations<Integer> {
    @Override
    public Integer add(Integer number1, Integer number2) {
        return number1 + number2;
    }

    @Override
    public Integer subtract(Integer number1, Integer number2) {
        return number1 - number2;
    }

    @Override
    public Integer divide(Integer number1, Integer number2) {
        if (number2 == 0) {
            throw new DivisionByZero(this + ": " + number2 + " = 0");
        }
        return number1 / number2;
    }

    @Override
    public Integer multiply(Integer number1, Integer number2) {
        return number1 * number2;
    }

    @Override
    public Integer getValue(Number number) {
        return number.intValue();
    }

    @Override
    public Integer negate(Integer number) {
        return -number;
    }

    @Override
    public Integer abs(Integer number) {
        return Math.abs(number);
    }

    @Override
    public Integer mod(Integer number1, Integer number2) {
        return number1 % number2;
    }

    @Override
    public Integer square(Integer number) {
        return multiply(number, number);
    }
}
