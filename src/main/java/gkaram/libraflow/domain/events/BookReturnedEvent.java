package gkaram.libraflow.domain.events;

import gkaram.libraflow.domain.shared.DomainEvent;

public record BookReturnedEvent(String bookId) implements DomainEvent {}
