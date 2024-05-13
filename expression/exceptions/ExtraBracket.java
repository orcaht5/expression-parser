package expression.exceptions;

public class ExtraBracket extends ParsingException {
    public ExtraBracket(int position, String bracket) {
        super("Extra bracket in position: " + position + ", '" + bracket + "'");
    }
}
