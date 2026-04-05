package gkaram.libraflow.infraestructure.db.reservation;

import gkaram.libraflow.domain.entities.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class ReservationEntity {

    @Id
    private UUID id;
    private UUID readerId;
    private UUID bookId;
    private LocalDateTime reservedAt;
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private Integer queuePosition;
}
