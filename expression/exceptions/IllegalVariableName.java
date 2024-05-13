package expression.exceptions;

public class IllegalVariableName extends ParsingException {
    public IllegalVariableName(int position, String name) {
        super("Illegal name of a variable at " + position + ": '" + name + "'");
    }
}
