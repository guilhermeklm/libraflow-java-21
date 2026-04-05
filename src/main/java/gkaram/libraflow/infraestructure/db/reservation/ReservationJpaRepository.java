package gkaram.libraflow.infraestructure.db.reservation;

import gkaram.libraflow.domain.entities.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, UUID> {

    boolean existsByBookIdAndStatus(UUID bookId, ReservationStatus status);

    Optional<ReservationEntity> findFirstByBookIdAndStatusOrderByQueuePositionAsc(UUID bookId, ReservationStatus status);
}
