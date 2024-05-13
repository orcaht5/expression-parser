package expression.exceptions;

import expression.Pow10;
import expression.PriorityOperation;

public class CheckedPow10 extends Pow10 {
    final PriorityOperation expression;
    public CheckedPow10(PriorityOperation expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int eval = expression.evaluate(x, y, z);
        if (eval > 0) {
            if (eval > Math.log10(Integer.MAX_VALUE)) {
                throw new OverflowException("Can't raise 10 to the power of " + eval);
            }
        }
        if (eval < 0) {
            throw new PowOnNegative(eval);
        }
        return (int)Math.pow(10, eval);
    }
}
