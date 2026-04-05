package gkaram.libraflow.domain.usecases;

public interface BaseUseCase<Input, Output> {
    Output execute(Input input);
}
