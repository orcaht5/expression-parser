package expression.exceptions;

import expression.Divide;
import expression.PriorityOperation;

public class CheckedDivide extends Divide {
    private final PriorityOperation expression1;
    private final PriorityOperation expression2;
    public CheckedDivide(PriorityOperation expression1, PriorityOperation expression2) {
        super(expression1, expression2);
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int evalExpr1 = expression1.evaluate(x, y, z);
        int evalExpr2 = expression2.evaluate(x, y, z);
        if (evalExpr1 == Integer.MIN_VALUE && evalExpr2 == -1) {
            throw new OverflowException(this + ": can't divide " + evalExpr1 + " = " + expression1 + " and " + evalExpr2 + " = " + expression2);
        }
        if (evalExpr2 == 0) {
            throw new DivisionByZero(this + ": " + expression2 + " = 0");
        }
        return evalExpr1 / evalExpr2;
    }
}
