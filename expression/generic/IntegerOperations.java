package expression.generic;

import expression.exceptions.DivisionByZero;
import expression.exceptions.OverflowException;

public class IntegerOperations implements GenericOperations<Integer> {
    @Override
    public Integer add(Integer number1, Integer number2) {
        if (number2 > 0 && number1 > Integer.MAX_VALUE - number2 ||
                number2 < 0 && number1 < Integer.MIN_VALUE - number2) {
            throw new OverflowException(this + ": can't sum " + number1 + " and " + number2);
        }
        return number1 + number2;
    }

    @Override
    public Integer subtract(Integer number1, Integer number2) {
        if (number2 < 0 && number1 > Integer.MAX_VALUE + number2 ||
                number2 > 0 && number1 < Integer.MIN_VALUE + number2) {
            throw new OverflowException(this + ": can't subtract " + number1 + " and " + number2);
        }
        return number1 - number2;
    }

    @Override
    public Integer divide(Integer number1, Integer number2) {
        if (number1 == Integer.MIN_VALUE && number2 == -1) {
            throw new OverflowException(this + ": can't divide " + number1 + " and " + number2);
        }
        if (number2 == 0) {
            throw new DivisionByZero(this + ": " + number2 + " = 0");
        }
        return number1 / number2;
    }

    @Override
    public Integer multiply(Integer number1, Integer number2) {
        if (number1 == -1 && number2 == Integer.MIN_VALUE ||
                number2 == -1 && number1 == Integer.MIN_VALUE ||
                number1 != 0 && (number1 * number2) / number1 != number2) {
            throw new OverflowException(this + ": can't multiply " + number1 + " and " + number2);
        }
        return number1 * number2;
    }

    @Override
    public Integer getValue(Number number) {
        return number.intValue();
    }

    @Override
    public Integer negate(Integer number) {
        if (number == Integer.MIN_VALUE) {
            throw new OverflowException("Can't negate " + number);
        }
        return -number;
    }

    @Override
    public Integer abs(Integer number) {
        if (number == Integer.MIN_VALUE) {
            throw new OverflowException("Can't take absolute value from a number " + number);
        }
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
