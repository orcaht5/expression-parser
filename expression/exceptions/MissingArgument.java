package expression.exceptions;

public class MissingArgument extends ParsingException {
    public MissingArgument(String note, int position) {
        super("Missing argument " + note + " at " + position);
    }
}
