package expression.exceptions;

public class LogOfNegativeOrZero extends RuntimeException {
    LogOfNegativeOrZero(int number) {
        super("Logarithm of a non-positive number: " + number);
    }
}
