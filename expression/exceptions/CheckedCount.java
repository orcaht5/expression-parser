package expression.exceptions;

import expression.Count;
import expression.PriorityOperation;

public class CheckedCount extends Count {
    public CheckedCount(PriorityOperation expression) {
        super(expression);
    }
}
