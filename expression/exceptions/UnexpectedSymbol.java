package expression.exceptions;

public class UnexpectedSymbol extends ParsingException {
    public UnexpectedSymbol(int position, char symbol) {
        super("Unexpected symbol at " + position + ": " + symbol);
    }
}
