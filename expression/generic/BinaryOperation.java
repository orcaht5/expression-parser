package expression.generic;

@FunctionalInterface
public interface BinaryOperation<T> {
    T getBinaryOperation(
            T eval1,
            T eval2,
            GenericOperations<T> operator
    );
}
