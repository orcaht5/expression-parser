package expression.generic;

public class ShortOperations implements GenericOperations<Short> {
    @Override
    public Short add(Short number1, Short number2) {
        return (short) (number1 + number2);
    }

    @Override
    public Short subtract(Short number1, Short number2) {
        return (short) (number1 - number2);
    }

    @Override
    public Short divide(Short number1, Short number2) {
        return (short) (number1 / number2);
    }

    @Override
    public Short multiply(Short number1, Short number2) {
        return (short) (number1 * number2);
    }

    @Override
    public Short getValue(Number number) {
        return number.shortValue();
    }

    @Override
    public Short negate(Short number) {
        return (short) -number;
    }

    @Override
    public Short abs(Short number) {
        return number >= 0 ? number : negate(number);
    }

    @Override
    public Short mod(Short number1, Short number2) {
        return subtract(number1, multiply(divide(number1, number2), number2));
    }

    @Override
    public Short square(Short number) {
        return multiply(number, number);
    }
}
