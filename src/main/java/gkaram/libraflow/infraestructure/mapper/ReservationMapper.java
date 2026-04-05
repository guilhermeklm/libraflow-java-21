package gkaram.libraflow.infraestructure.mapper;

import gkaram.libraflow.domain.entities.Reservation;
import gkaram.libraflow.infraestructure.db.reservation.ReservationEntity;

import java.util.UUID;

public class ReservationMapper {

    public static ReservationEntity toEntity(Reservation reservation) {
        return new ReservationEntity(
                UUID.fromString(reservation.getId()),
                UUID.fromString(reservation.getReaderId()),
                UUID.fromString(reservation.getBookId()),
                reservation.getReservedAt(),
                reservation.getExpiresAt(),
                reservation.getStatus(),
                reservation.getQueuePosition()
        );
    }

    public static Reservation toDomain(ReservationEntity entity) {
        return Reservation.Restore(
                entity.getId().toString(),
                entity.getReaderId().toString(),
                entity.getBookId().toString(),
                entity.getReservedAt(),
                entity.getExpiresAt(),
                entity.getStatus(),
                entity.getQueuePosition()
        );
    }
}
