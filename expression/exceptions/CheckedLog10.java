package expression.exceptions;

import expression.Log10;
import expression.PriorityOperation;

public class CheckedLog10 extends Log10 {
    final PriorityOperation expression;
    public CheckedLog10(PriorityOperation expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int eval = expression.evaluate(x, y, z);
        if (eval <= 0) {
            throw new LogOfNegativeOrZero(eval);
        }
        return (int)Math.log10(eval);
    }
}
