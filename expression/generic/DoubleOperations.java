package expression.generic;


public class DoubleOperations implements GenericOperations<Double> {
    @Override
    public Double add(Double number1, Double number2) {
        return number1 + number2;
    }

    @Override
    public Double subtract(Double number1, Double number2) {
        return number1 - number2;
    }

    @Override
    public Double divide(Double number1, Double number2) {
        return number1 / number2;
    }

    @Override
    public Double multiply(Double number1, Double number2) {
        return number1 * number2;
    }

    @Override
    public Double getValue(Number number) {
        return number.doubleValue();
    }

    @Override
    public Double negate(Double number) {
        return -number;
    }

    @Override
    public Double abs(Double number) {
        return Math.abs(number);
    }

    @Override
    public Double mod(Double number1, Double number2) {
        return number1 % number2;
    }

    @Override
    public Double square(Double number) {
        return multiply(number, number);
    }
}
