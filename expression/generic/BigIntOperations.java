package expression.generic;

import expression.exceptions.DivisionByZero;

import java.math.BigInteger;
import java.util.Objects;

public class BigIntOperations implements GenericOperations<BigInteger> {
    @Override
    public BigInteger add(BigInteger number1, BigInteger number2) {
        return number1.add(number2);
    }

    @Override
    public BigInteger subtract(BigInteger number1, BigInteger number2) {
        return number1.subtract(number2);
    }

    @Override
    public BigInteger divide(BigInteger number1, BigInteger number2) {
        if (Objects.equals(number2, BigInteger.valueOf(0))) {
            throw new DivisionByZero(this + ": " + number2 + " = 0");
        }
        return number1.divide(number2);
    }

    @Override
    public BigInteger multiply(BigInteger number1, BigInteger number2) {
        return number1.multiply(number2);
    }

    @Override
    public BigInteger getValue(Number number) {
        return BigInteger.valueOf(number.intValue());
    }

    @Override
    public BigInteger negate(BigInteger number) {
        return number.negate();
    }

    @Override
    public BigInteger abs(BigInteger number) {
        return number.abs();
    }

    @Override
    public BigInteger mod(BigInteger number1, BigInteger number2) {
        if (number2.compareTo(BigInteger.valueOf(0)) != 1) {
            throw new RuntimeException("Can't take " + number1 + " % " + number2);
        }
        return number1.mod(number2);
    }

    @Override
    public BigInteger square(BigInteger number) {
        return multiply(number, number);
    }
}
