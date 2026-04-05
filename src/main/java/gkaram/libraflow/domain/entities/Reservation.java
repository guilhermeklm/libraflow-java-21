package gkaram.libraflow.domain.entities;

import gkaram.libraflow.domain.shared.Result;

import java.time.LocalDateTime;

public class Reservation {

    private String id;
    private String readerId;
    private String bookId;
    private LocalDateTime reservedAt;
    private LocalDateTime expiresAt;
    private ReservationStatus status;
    private Integer queuePosition;

    private Reservation(String id, String readerId, String bookId, LocalDateTime reservedAt, LocalDateTime expiresAt, ReservationStatus status, Integer queuePosition) {
        this.id = id;
        this.readerId = readerId;
        this.bookId = bookId;
        this.reservedAt = reservedAt;
        this.expiresAt = expiresAt;
        this.status = status;
        this.queuePosition = queuePosition;
    }

    public static Result<Reservation> New(String id, String readerId, String bookId, Integer queuePosition) {
        if (readerId == null || readerId.isBlank())
            return new Result.Failure<>("Leitor não informado", Result.ErrorStatus.VALIDATION_ERROR);
        if (bookId == null || bookId.isBlank())
            return new Result.Failure<>("Livro não informado", Result.ErrorStatus.VALIDATION_ERROR);

        return new Result.Success<>(new Reservation(id, readerId, bookId, LocalDateTime.now(), null, ReservationStatus.QUEUE, queuePosition));
    }

    public static Reservation Restore(String id, String readerId, String bookId, LocalDateTime reservedAt, LocalDateTime expiresAt, ReservationStatus status, Integer queuePosition) {
        return new Reservation(id, readerId, bookId, reservedAt, expiresAt, status, queuePosition);
    }

    public void notifyUser() {
        this.status = ReservationStatus.NOTIFIED;
    }

    public String getId() { return id; }
    public String getReaderId() { return readerId; }
    public String getBookId() { return bookId; }
    public LocalDateTime getReservedAt() { return reservedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public ReservationStatus getStatus() { return status; }
    public Integer getQueuePosition() { return queuePosition; }
}
