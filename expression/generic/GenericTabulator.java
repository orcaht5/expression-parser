package expression.generic;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] ans = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        PriorityExpression<?> expr = switch (mode) {
            case "i" -> new ExpressionParser<Integer>().parse(expression, new IntegerOperations());
            case "d" -> new ExpressionParser<Double>().parse(expression, new DoubleOperations());
            case "bi" -> new ExpressionParser<BigInteger>().parse(expression, new BigIntOperations());
            case "s" -> new ExpressionParser<Short>().parse(expression, new ShortOperations());
            case "l" -> new ExpressionParser<Long>().parse(expression, new LongOperations());
            case "u" -> new ExpressionParser<Integer>().parse(expression, new UnsignedIntOperations());
            default -> null;
        };
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        ans[x - x1][y - y1][z - z1] = expr.evaluate(x, y, z);
                    } catch (RuntimeException e) {
                        ans[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Not enough arguments");
            return;
        }
        GenericTabulator tabulator = new GenericTabulator();
        Object[][][] ans = tabulator.tabulate(args[0], args[1],
                -2, 2, -2, 2, -2, 2);
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    System.out.println("x = " + x + ", y = " +
                            y + ", z = " + z + ", result = " + ans[x + 2][y + 2][z + 2]);
                }
            }
        }
    }
}
