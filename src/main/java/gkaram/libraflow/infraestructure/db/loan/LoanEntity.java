package gkaram.libraflow.infraestructure.db.loan;

import gkaram.libraflow.domain.entities.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loans")
public class LoanEntity {

    @Id
    private UUID id;
    private UUID readerId;
    private UUID bookId;
    private LocalDateTime loanDate;
    private LocalDate dueDate;
    private LocalDateTime returnedAt;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    private Integer renewalCount;
}
