package gkaram.libraflow.infraestructure.messaging;

import gkaram.libraflow.domain.dto.NotifyNextInQueueInput;
import gkaram.libraflow.domain.entities.repository.ReservationRepository;
import gkaram.libraflow.domain.entities.repository.UserRepository;
import gkaram.libraflow.domain.events.BookReturnedEvent;
import gkaram.libraflow.domain.shared.EmailSender;
import gkaram.libraflow.domain.usecases.NotifyNextInQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReservationConsumer {

    private static final Logger log = LoggerFactory.getLogger(ReservationConsumer.class);

    private final NotifyNextInQueue notifyNextInQueue;

    public ReservationConsumer(ReservationRepository reservationRepository, UserRepository userRepository, EmailSender emailSender) {
        this.notifyNextInQueue = new NotifyNextInQueue(reservationRepository, userRepository, emailSender);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_BOOK_RETURNED)
    public void onBookReturned(BookReturnedEvent event) {
        var output = notifyNextInQueue.execute(new NotifyNextInQueueInput(event.bookId()));

        if (output.error() != null) {
            log.warn("Nenhuma reserva para notificar: {}", output.error().message());
            return;
        }

        log.info("Reserva {} notificada — livro {} disponível", output.reservationId(), event.bookId());
    }
}
