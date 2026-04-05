package gkaram.libraflow.domain.shared;

public sealed interface Result<T> permits Result.Success, Result.Failure {
    record Success<T>(T value) implements Result<T> {}
    record Failure<T>(String message, ErrorStatus status) implements Result<T> {}

    enum ErrorStatus {
        VALIDATION_ERROR,
        NOT_FOUND,
        CONFLICT,
        UNAUTHORIZED
    }

    default boolean isFailure() {
        return this instanceof Failure<T>;
    }

    default T value() {
        return ((Success<T>) this).value;
    }

    default String errorMessage() {
        return ((Failure<T>) this).message();
    }

    default ErrorStatus errorStatus() {
        return ((Failure<T>) this).status();
    }
}
