package gkaram.libraflow.domain.usecases;

import gkaram.libraflow.domain.dto.ErrorOutput;
import gkaram.libraflow.domain.dto.NotifyNextInQueueInput;
import gkaram.libraflow.domain.dto.NotifyNextInQueueOutput;
import gkaram.libraflow.domain.entities.repository.ReservationRepository;
import gkaram.libraflow.domain.entities.repository.UserRepository;
import gkaram.libraflow.domain.shared.EmailSender;
import gkaram.libraflow.domain.shared.Result;

public class NotifyNextInQueue implements BaseUseCase<NotifyNextInQueueInput, NotifyNextInQueueOutput> {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final EmailSender emailSender;

    public NotifyNextInQueue(ReservationRepository reservationRepository, UserRepository userRepository, EmailSender emailSender) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.emailSender = emailSender;
    }

    @Override
    public NotifyNextInQueueOutput execute(NotifyNextInQueueInput input) {
        var reservationOpt = reservationRepository.findFirstInQueue(input.bookId());

        if (reservationOpt.isEmpty()) {
            return new NotifyNextInQueueOutput(null,
                    new ErrorOutput("Nenhuma reserva na fila para o livro " + input.bookId(), Result.ErrorStatus.NOT_FOUND.name()));
        }

        var reservation = reservationOpt.get();

        var userOpt = userRepository.findById(reservation.getReaderId());
        if (userOpt.isEmpty()) {
            return new NotifyNextInQueueOutput(null,
                    new ErrorOutput("Usuário não encontrado: " + reservation.getReaderId(), Result.ErrorStatus.NOT_FOUND.name()));
        }

        emailSender.send(
                userOpt.get().getEmail(),
                "Livro disponível para retirada",
                "Olá! O livro que você reservou está disponível para retirada."
        );

        reservation.notifyUser();
        reservationRepository.save(reservation);

        return new NotifyNextInQueueOutput(reservation.getId(), null);
    }
}
