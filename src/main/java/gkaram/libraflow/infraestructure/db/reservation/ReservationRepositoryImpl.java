package gkaram.libraflow.infraestructure.db.reservation;

import gkaram.libraflow.domain.entities.Reservation;
import gkaram.libraflow.domain.entities.ReservationStatus;
import gkaram.libraflow.domain.entities.repository.ReservationRepository;
import gkaram.libraflow.infraestructure.mapper.ReservationMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    public ReservationRepositoryImpl(ReservationJpaRepository reservationJpaRepository) {
        this.reservationJpaRepository = reservationJpaRepository;
    }

    @Override
    public boolean hasQueuedReservations(String bookId) {
        return reservationJpaRepository.existsByBookIdAndStatus(UUID.fromString(bookId), ReservationStatus.QUEUE);
    }

    @Override
    public Optional<Reservation> findFirstInQueue(String bookId) {
        return reservationJpaRepository
                .findFirstByBookIdAndStatusOrderByQueuePositionAsc(UUID.fromString(bookId), ReservationStatus.QUEUE)
                .map(ReservationMapper::toDomain);
    }

    @Override
    public void save(Reservation reservation) {
        reservationJpaRepository.save(ReservationMapper.toEntity(reservation));
    }
}
