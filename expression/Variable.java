package expression;

import java.util.Objects;

public class Variable implements PriorityOperation {
    private final String variable;

    public Variable(final String variable) {
        this.variable = variable;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (variable) {
            case "x" -> {
                return x;
            }
            case "y" -> {
                return y;
            }
            case "z" -> {
                return z;
            }
            default -> throw new IllegalArgumentException("Only three variables are allowed: x, y, z");
        }
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public boolean isAssociative() {
        return true;
    }

    @Override
    public boolean isMatching() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable1 = (Variable) o;
        return Objects.equals(variable, variable1.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable, getClass());
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public String toMiniString() {
        return variable;
    }
}