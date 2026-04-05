package gkaram.libraflow.domain.entities.repository;

import gkaram.libraflow.domain.entities.Reservation;

import java.util.Optional;

public interface ReservationRepository {
    boolean hasQueuedReservations(String bookId);
    Optional<Reservation> findFirstInQueue(String bookId);
    void save(Reservation reservation);
}
