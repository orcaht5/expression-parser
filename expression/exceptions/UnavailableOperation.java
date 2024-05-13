package expression.exceptions;

public class UnavailableOperation extends ParsingException {
    public UnavailableOperation(int position, String name) {
        super("Unavailable operation at " + position + ": " + name);
    }
}
