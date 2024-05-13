package expression.exceptions;

public class InvalidNumber extends ParsingException {
    public InvalidNumber(int position, String name) {
        super("Invalid number at " + position + ": '" + name + "'");
    }
}
