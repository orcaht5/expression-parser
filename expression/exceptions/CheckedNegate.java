package expression.exceptions;

import expression.Negate;
import expression.PriorityOperation;

public class CheckedNegate extends Negate {
    final PriorityOperation expression;
    public CheckedNegate(PriorityOperation expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int eval = expression.evaluate(x, y, z);
        if (eval == Integer.MIN_VALUE) {
            throw new OverflowException(this + ": can't negate " + eval + " (" + expression + ")");
        }
        return super.evaluate(x, y, z);
    }
}
