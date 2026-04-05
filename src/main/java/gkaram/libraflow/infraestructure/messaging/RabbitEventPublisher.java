package gkaram.libraflow.infraestructure.messaging;

import gkaram.libraflow.domain.events.BookReturnedEvent;
import gkaram.libraflow.domain.shared.DomainEvent;
import gkaram.libraflow.domain.shared.EventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        if (event instanceof BookReturnedEvent bookReturnedEvent) {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_BOOKS,
                    RabbitMQConfig.ROUTING_KEY_BOOK_RETURNED,
                    bookReturnedEvent
            );
        }
    }
}
