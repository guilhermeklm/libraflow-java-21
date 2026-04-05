package gkaram.libraflow.domain.entities;

import gkaram.libraflow.domain.shared.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Loan {

    private String id;
    private String readerId;
    private String bookId;
    private LocalDateTime loanDate;
    private LocalDate dueDate;
    private LocalDateTime returnedAt;
    private LoanStatus status;
    private Integer renewalCount;

    private Loan(String id, String readerId, String bookId, LocalDateTime loanDate, LocalDate dueDate, LocalDateTime returnedAt, LoanStatus status, Integer renewalCount) {
        this.id = id;
        this.readerId = readerId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnedAt = returnedAt;
        this.status = status;
        this.renewalCount = renewalCount;
    }

    public static Result<Loan> New(String id, String readerId, String bookId, LocalDate dueDate) {
        if (readerId == null || readerId.isBlank())
            return new Result.Failure<>("Leitor não informado", Result.ErrorStatus.VALIDATION_ERROR);
        if (bookId == null || bookId.isBlank())
            return new Result.Failure<>("Livro não informado", Result.ErrorStatus.VALIDATION_ERROR);
        if (dueDate == null || dueDate.isBefore(LocalDate.now()))
            return new Result.Failure<>("Data de devolução inválida", Result.ErrorStatus.VALIDATION_ERROR);

        return new Result.Success<>(new Loan(id, readerId, bookId, LocalDateTime.now(), dueDate, null, LoanStatus.ACTIVE, 0));
    }

    public static Loan Restore(String id, String readerId, String bookId, LocalDateTime loanDate, LocalDate dueDate, LocalDateTime returnedAt, LoanStatus status, Integer renewalCount) {
        return new Loan(id, readerId, bookId, loanDate, dueDate, returnedAt, status, renewalCount);
    }

    public Result<Void> returnLoan() {
        if (this.status == LoanStatus.RETURNED)
            return new Result.Failure<>("Empréstimo já devolvido", Result.ErrorStatus.VALIDATION_ERROR);

        this.status = LoanStatus.RETURNED;
        this.returnedAt = LocalDateTime.now();
        return new Result.Success<>(null);
    }

    public String getId() { return id; }
    public String getReaderId() { return readerId; }
    public String getBookId() { return bookId; }
    public LocalDateTime getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public LoanStatus getStatus() { return status; }
    public Integer getRenewalCount() { return renewalCount; }
}
