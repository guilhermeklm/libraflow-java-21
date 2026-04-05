package gkaram.libraflow.domain.shared;

public interface EventPublisher {
    void publish(DomainEvent event);
}
