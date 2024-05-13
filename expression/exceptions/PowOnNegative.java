package expression.exceptions;

public class PowOnNegative extends RuntimeException {
    PowOnNegative(int number) {
        super("Negative power of a number: " + number);
    }
}
