package expression.exceptions;

import expression.Multiply;
import expression.PriorityOperation;

public class CheckedMultiply extends Multiply {
    private final PriorityOperation expression1;
    private final PriorityOperation expression2;
    public CheckedMultiply(PriorityOperation expression1, PriorityOperation expression2) {
        super(expression1, expression2);
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int evalExpr1 = expression1.evaluate(x, y, z);
        int evalExpr2 = expression2.evaluate(x, y, z);
        if (evalExpr1 == 0 || evalExpr2 == 0) {
            return 0;
        }
        int mult = evalExpr1 * evalExpr2;
        if (evalExpr1 == -1 && evalExpr2 == Integer.MIN_VALUE || mult / evalExpr1 != evalExpr2) {
            throw new OverflowException(this + ": can't multiply " + evalExpr1 + " = " + expression1 + " and " + evalExpr2 + " = " + expression2);
        }
        return mult;
    }
}
