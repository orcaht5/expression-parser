package expression.parser;

import expression.*;
import expression.exceptions.TripleParser;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ExpressionParser implements TripleParser {
    private enum Operation {
        PLUS, MINUS, DIVIDE, MULTIPLY, EMPTY, LP, RP, SET, CLEAR, COUNT, NEGATE
    }
    private static final Map<String, Operation> BINARY_OPERATIONS = Map.of(
            "+", Operation.PLUS,
            "-", Operation.MINUS,
            "*", Operation.MULTIPLY,
            "/", Operation.DIVIDE,
            "set", Operation.SET,
            "clear", Operation.CLEAR
    );

    private static final Map<String, Operation> PARANTHES = Map.of(
            "(", Operation.LP,
            ")", Operation.RP
    );
    private static final Map<String, Operation> UNARY_OPERATIONS_AND_PARENTHESES = Map.of(
            "-", Operation.NEGATE,
            "count", Operation.COUNT,
            "(", Operation.LP,
            ")", Operation.RP,
            "", Operation.EMPTY
    );

    private static final List<Character> ONE_SYMBOL_OPERATIONS = List.of('+', '-', '*', '/', '(', ')');

    private static final List<String> VARIABLES = List.of("x", "y", "z");

    public TripleExpression parse(final String source) {
        return parse(new StringSource(source));
    }

    public static TripleExpression parse(final CharSource source) {
        return new StingExpressionParser(source).parseExpression();
    }

    private static class StingExpressionParser extends BaseParser {
        String binaryOperation = "";
        boolean haveBinaryOperation;
        boolean haveUnaryOperation;
        boolean haveRightParenthesis;

        public StingExpressionParser(final CharSource source) {
            super(source);
        }

        public TripleExpression parseExpression() {
            final TripleExpression result = parseElement();
            if (eof()) {
                return result;
            }
            throw error("End of expression expected");
        }

        private TripleExpression parseElement() {
            skipWhitespace();
            final TripleExpression result = parseSetOrClear();
            skipWhitespace();
            return result;
        }

        private PriorityOperation parseSetOrClear() {
            PriorityOperation result = parseAddOrSubtract();
            while (true) {
                checkBinaryOperation();
                if (haveRightParenthesis) {
                    return result;
                }
                if (haveBinaryOperation) {
                    haveBinaryOperation = false;
                    switch (BINARY_OPERATIONS.get(binaryOperation)) {
                        case SET -> result = new Set(result, parseAddOrSubtract());
                        case CLEAR -> result = new Clear(result, parseAddOrSubtract());
                        default -> {
                            haveBinaryOperation = true;
                            return result;
                        }
                    }
                } else {
                    return result;
                }
            }
        }

        private PriorityOperation parseAddOrSubtract() {
            PriorityOperation result = parseMultOrDiv();
            while (true) {
                checkBinaryOperation();
                if (haveRightParenthesis) {
                    return result;
                }
                if (haveBinaryOperation) {
                    haveBinaryOperation = false;
                    switch (BINARY_OPERATIONS.get(binaryOperation)) {
                        case PLUS -> result = new Add(result, parseMultOrDiv());
                        case MINUS -> result = new Subtract(result, parseMultOrDiv());
                        default -> {
                            haveBinaryOperation = true;
                            return result;
                        }
                    }
                } else {
                    return result;
                }
            }
        }

        private PriorityOperation parseMultOrDiv() {
            PriorityOperation result = parseUnary("");
            while (true) {
                checkBinaryOperation();
                if (haveRightParenthesis) {
                    return result;
                }
                if (haveBinaryOperation) {
                    haveBinaryOperation = false;
                    switch (BINARY_OPERATIONS.get(binaryOperation)) {
                        case MULTIPLY -> result = new Multiply(result, parseUnary(""));
                        case DIVIDE -> result = new Divide(result, parseUnary(""));
                        default -> {
                            haveBinaryOperation = true;
                            return result;
                        }
                    }
                } else {
                    return result;
                }
            }
        }

        private PriorityOperation parseUnary(final String unaryOperation) {
            String sequence;
            if (!haveUnaryOperation) {
                skipWhitespace();
                sequence = between('0', '9') ?  "" : getIdentifier();
            } else {
                sequence = unaryOperation;
                haveUnaryOperation = false;
            }

            final boolean wasSeparator = isWhitespaceOrLineSeparator();
            boolean negate = false;
            skipWhitespace();
            if (!UNARY_OPERATIONS_AND_PARENTHESES.containsKey(sequence)) {
                checkVariableName(sequence);
                return new Variable(sequence);
            }
            if (!wasSeparator && UNARY_OPERATIONS_AND_PARENTHESES.get(sequence) == Operation.NEGATE && between('1', '9')) {
                sequence = "";
                negate = true;
            }

            final PriorityOperation result;
            if (UNARY_OPERATIONS_AND_PARENTHESES.get(sequence) == Operation.LP) {
                result = newParentheses();
            } else if (between('0', '9')) {
                result = new Const(takeInteger(negate));
            } else {
                final String name = getIdentifier();
                if (UNARY_OPERATIONS_AND_PARENTHESES.containsKey(name)) {
                    haveUnaryOperation = true;
                    result = parseUnary(name);
                } else {
                    checkVariableName(name);
                    result = new Variable(name);
                }
            }
            skipWhitespace();
            return switch (UNARY_OPERATIONS_AND_PARENTHESES.get(sequence)) {
                case NEGATE -> new Negate(result);
                case COUNT -> new Count(result);
                default -> result;
            };
        }

        void checkBinaryOperation() {
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
                if (PARANTHES.containsKey(binaryOperation)) {
                    switch (PARANTHES.get(binaryOperation)) {
                        case LP -> throw error("Left parenthesis not allowed here");
                        case RP -> haveRightParenthesis = true;
                    }
                } else {
                    throw error("Unavailable operation '" + binaryOperation + "'");
                }
            } else {
                haveBinaryOperation = true;
            }
        }

        private PriorityOperation newParentheses() {
            final PriorityOperation result = parseSetOrClear();
            if (!haveRightParenthesis) {
                throw error("')' expected");
            }
            haveRightParenthesis = false;
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
            }
            while (!eof() && Character.isJavaIdentifierPart(getChar())) {
                operation.append(take());
            }
            return operation.toString();
        }

        private void takeDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(take());
            }
        }

        private int takeInteger(boolean negate) {
            skipWhitespace();
            final StringBuilder sb = new StringBuilder();
            if (take('0')) {
                sb.append('0');
            } else if (between('1', '9')) {
                takeDigits(sb);
            } else {
                throw error("Invalid number " + sb);
            }
            skipWhitespace();
            return negate ? -Integer.parseUnsignedInt(sb.toString()) : Integer.parseUnsignedInt(sb.toString());
        }

        private void checkVariableName(final String name) {
            if (VARIABLES.contains(name)) {
                return;
            }
            throw error("Invalid name of variable '" + name + "'");
        }
    }
}
