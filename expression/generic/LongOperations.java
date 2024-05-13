package expression.generic;

public class LongOperations implements GenericOperations<Long> {
    @Override
    public Long add(Long number1, Long number2) {
        return number1 + number2;
    }

    @Override
    public Long subtract(Long number1, Long number2) {
        return number1 - number2;
    }

    @Override
    public Long divide(Long number1, Long number2) {
        return number1 / number2;
    }

    @Override
    public Long multiply(Long number1, Long number2) {
        return number1 * number2;
    }

    @Override
    public Long getValue(Number number) {
        return number.longValue();
    }

    @Override
    public Long negate(Long number) {
        return -number;
    }

    @Override
    public Long abs(Long number) {
        return Math.abs(number);
    }

    @Override
    public Long mod(Long number1, Long number2) {
        return number1 % number2;
    }

    @Override
    public Long square(Long number) {
        return multiply(number, number);
    }
}
