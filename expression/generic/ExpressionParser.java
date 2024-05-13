package expression.generic;

import expression.exceptions.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ExpressionParser<T> {
    private enum Operation {
        PLUS, MINUS, DIVIDE, MULTIPLY, LP, RP, NEGATE, MOD, ABS, SQUARE, OTHER
    }

    private static final Map<String, Operation> BINARY_OPERATIONS = Map.of(
            "+", Operation.PLUS,
            "-", Operation.MINUS,
            "*", Operation.MULTIPLY,
            "/", Operation.DIVIDE,
            "mod", Operation.MOD
    );

    private static final Map<Operation, Integer> OPERATION_LEVEL = Map.of(
            Operation.PLUS, 0,
            Operation.MINUS, 0,
            Operation.MULTIPLY, 1,
            Operation.DIVIDE, 1,
            Operation.MOD, 2
    );

    private static final Map<String, Operation> PARENTHESES = Map.of(
            "(", Operation.LP,
            ")", Operation.RP
    );
    private static final Map<String, Operation> UNARY_OPERATIONS = Map.of(
            "-", Operation.NEGATE,
            "abs", Operation.ABS,
            "square", Operation.SQUARE
    );

    private static final List<Character> ONE_SYMBOL_OPERATIONS = List.of('+', '-', '*', '/', '(', ')');

    private static final List<String> VARIABLES = List.of("x", "y", "z");
    private static final int UNARY_LEVEL = 3;

    public PriorityExpression<T> parse(final String source, final GenericOperations<T> operator) throws ParsingException {
        return parse(new StringSource(source), operator);
    }

    public PriorityExpression<T> parse(final CharSource source, final GenericOperations<T> operator) throws ParsingException {
        return new StingExpressionParser<T>(source, operator).parseExpression();
    }

    private static class StingExpressionParser<T> extends BaseParser {
        private String binaryOperation = "";
        private boolean haveBinaryOperation;
        private boolean haveRightParenthesis;
        private int countOfLeftParenthesis = 0;

        private final GenericOperations<T> operator;

        public StingExpressionParser(final CharSource source, final GenericOperations<T> operator) {
            super(source);
            this.operator = operator;
        }

        public PriorityExpression<T> parseExpression() throws ParsingException {
            final PriorityExpression<T> result = parseElement();
            if (eof()) {
                return result;
            }
            throw error("End of expression expected");
        }

        private PriorityExpression<T> parseElement() throws ParsingException {
            skipWhitespace();
            final PriorityExpression<T> result = parseBinary(0);
            skipWhitespace();
            return result;
        }

        private PriorityExpression<T> parseBinary(int level) throws ParsingException {
            PriorityExpression<T> result;
            if (level == UNARY_LEVEL) {
                result = parseUnary(false);
            } else {
                result = parseBinary(level + 1);
            }
            while (true) {
                checkBinaryOperation();
                if (haveRightParenthesis) {
                    return result;
                }
                if (haveBinaryOperation) {
                    if (OPERATION_LEVEL.get(BINARY_OPERATIONS.get(binaryOperation)) != level) {
                        haveBinaryOperation = true;
                        return result;
                    }
                    haveBinaryOperation = false;
                    BinaryOperation<T> operation = null;
                    switch (BINARY_OPERATIONS.get(binaryOperation)) {
                        case PLUS -> operation = (eval1, eval2, operator) -> operator.add(eval1, eval2);
                        case MINUS -> operation = (eval1, eval2, operator) -> operator.subtract(eval1, eval2);
                        case MULTIPLY -> operation = (eval1, eval2, operator) -> operator.multiply(eval1, eval2);
                        case DIVIDE -> operation = (eval1, eval2, operator) -> operator.divide(eval1, eval2);
                        case MOD -> operation = (eval1, eval2, operator) -> operator.mod(eval1, eval2);
                    };
                    result = new BinaryExpression<T>(operation, operator, result, parseBinary(level + 1));
                } else {
                    return result;
                }
            }
        }

        private PriorityExpression<T> parseUnary(boolean haveUnaryOperation) throws ParsingException {
            skipWhitespace();
            String sequence = getIdentifier();
            if (sequence.equals("") && !between('0', '9')) {
                if (!isWhitespaceOrLineSeparator() && !eof()) {
                    throw new UnexpectedSymbol(getPos(), getChar());
                } else {
                    if (haveUnaryOperation) {
                        return null;
                    }
                    throw new MissingArgument("after " + binaryOperation, getPos() - 1);
                }
            } else if (!ONE_SYMBOL_OPERATIONS.contains(getChar())
                    && !isWhitespaceOrLineSeparator() && !eof() && !between('0', '9')
                    && !Character.isJavaIdentifierStart(getChar())) {
                throw new UnexpectedSymbol(getPos(), getChar());
            }
            final boolean wasSeparator = isWhitespaceOrLineSeparator();
            boolean negate = false;
            skipWhitespace();
            if (!(sequence.equals("") && between('0', '9'))
                    && !UNARY_OPERATIONS.containsKey(sequence)
                    && !PARENTHESES.containsKey(sequence)) {
                if (BINARY_OPERATIONS.containsKey(sequence)) {
                    throw new MissingArgument("before " + sequence, getPos() - 1);
                }
                checkVariableName(sequence);
                return new Variable<T>(sequence, operator);
            }

            if (!wasSeparator &&
                    UNARY_OPERATIONS.getOrDefault(sequence, Operation.OTHER) == Operation.NEGATE && between('1', '9')) {
                sequence = "";
                negate = true;
            }

            if (PARENTHESES.getOrDefault(sequence, Operation.OTHER) == Operation.RP) {
                if (haveUnaryOperation) {
                    return null;
                }
                if (haveBinaryOperation) {
                    throw new MissingArgument("after " + binaryOperation, getPos() - 1);
                }
                throw new MissingArgument("in ()", getPos() - 1);
            }

            if (UNARY_OPERATIONS.containsKey(sequence)) {
                return parseUnaryOperation(sequence);
            } else if (PARENTHESES.getOrDefault(sequence, Operation.OTHER) == Operation.LP) {
                return newParentheses();
            } else {
                int number = takeInteger(negate);
                if (!ONE_SYMBOL_OPERATIONS.contains(getChar()) && !isWhitespaceOrLineSeparator() && !eof() && !between('0', '9') && !Character.isJavaIdentifierStart(getChar())) {
                    throw new UnexpectedSymbol(getPos(), getChar());
                }
                return new Const<T>(number, operator);
            }
        }

        private PriorityExpression<T> parseUnaryOperation(String unaryOperation) throws ParsingException {
            int position = getPos();
            final PriorityExpression<T> result = parseUnary(true);
            if (result == null) {
                throw new MissingArgument("after " + unaryOperation, position);
            }
            skipWhitespace();
            return switch (UNARY_OPERATIONS.getOrDefault(unaryOperation, Operation.OTHER)) {
                case NEGATE -> new Negate<T>(result, operator);
                case ABS -> new Abs<T>(result, operator);
                case SQUARE -> new Square<T>(result, operator);
                default -> result;
            };
        }

        void checkBinaryOperation() throws ParsingException {
            if (haveRightParenthesis) {
                return;
            }
            if (!haveBinaryOperation) {
                binaryOperation = getIdentifier();
            }
            haveBinaryOperation = false;
            haveRightParenthesis = false;
            if (Objects.equals(binaryOperation, "")) {
                return;
            }
            if (!BINARY_OPERATIONS.containsKey(binaryOperation)) {
                if (PARENTHESES.containsKey(binaryOperation)) {
                    switch (PARENTHESES.get(binaryOperation)) {
                        case LP -> throw new ExtraBracket(getPos(), "(");
                        case RP -> {
                            if (countOfLeftParenthesis == 0) {
                                throw new ExtraBracket(getPos(), ")");
                            }
                            haveRightParenthesis = true;
                        }
                    }
                } else {
                    throw new UnavailableOperation(getPos(), binaryOperation);
                }
            } else {
                haveBinaryOperation = true;
            }
        }

        private PriorityExpression<T> newParentheses() throws ParsingException {
            int leftParenthesisPosition = getPos();
            countOfLeftParenthesis++;
            final PriorityExpression<T> result = parseBinary(0);
            if (!haveRightParenthesis) {
                throw new ExtraBracket(leftParenthesisPosition, "(");
            }
            haveRightParenthesis = false;
            countOfLeftParenthesis--;
            return result;
        }

        private String getIdentifier() {
            skipWhitespace();
            if (ONE_SYMBOL_OPERATIONS.contains(getChar())) {
                return Character.toString(take());
            }
            final StringBuilder operation = new StringBuilder();
            if (Character.isJavaIdentifierStart(getChar())) {
                operation.append(take());
                while (!eof() && Character.isJavaIdentifierPart(getChar())) {
                    operation.append(take());
                }
            }
            return operation.toString();
        }

        private void takeDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(take());
            }
        }

        private int takeInteger(boolean negate) throws ParsingException {
            skipWhitespace();
            final StringBuilder sb = new StringBuilder();
            if (take('0')) {
                sb.append('0');
            } else if (between('1', '9')) {
                takeDigits(sb);
            } else {
                throw new InvalidNumber(getPos(), sb.toString());
            }
            skipWhitespace();
            String minAbsInteger = Integer.toString(Integer.MIN_VALUE).substring(1);
            String maxInteger = Integer.toString(Integer.MAX_VALUE);
            if (negate && (sb.length() > minAbsInteger.length() || sb.length() == minAbsInteger.length() && sb.toString().compareTo(minAbsInteger) > 0)) {
                throw new InvalidNumber(getPos(), '-' + sb.toString());
            }
            if (!negate && (sb.length() > maxInteger.length() || sb.length() == maxInteger.length() && sb.toString().compareTo(maxInteger) > 0)) {
                throw new InvalidNumber(getPos(), sb.toString());
            }
            return negate ? -Integer.parseUnsignedInt(sb.toString()) : Integer.parseUnsignedInt(sb.toString());
        }

        private void checkVariableName(final String name) throws IllegalVariableName {
            if (VARIABLES.contains(name)) {
                return;
            }
            throw new IllegalVariableName(getPos(), name);
        }
    }
}
