package expression.exceptions;

import expression.Add;
import expression.PriorityOperation;

public class CheckedAdd extends Add {
    private final PriorityOperation expression1;
    private final PriorityOperation expression2;

    public CheckedAdd(PriorityOperation expression1, PriorityOperation expression2) {
        super(expression1, expression2);
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int evalExpr1 = expression1.evaluate(x, y, z);
        int evalExpr2 = expression2.evaluate(x, y, z);
        if (evalExpr2 > 0 && evalExpr1 > Integer.MAX_VALUE - evalExpr2 ||
                evalExpr2 < 0 && evalExpr1 < Integer.MIN_VALUE - evalExpr2) {
            throw new OverflowException(this + ": can't sum " + evalExpr1 + " = " + expression1 + " and " + evalExpr2 + " = " + expression2);
        }
        return evalExpr1 + evalExpr2;
    }
}
