package gkaram.libraflow.infraestructure.mapper;

import gkaram.libraflow.domain.entities.Loan;
import gkaram.libraflow.infraestructure.db.loan.LoanEntity;

import java.util.UUID;

public class LoanMapper {

    public static LoanEntity toEntity(Loan loan) {
        return new LoanEntity(
                UUID.fromString(loan.getId()),
                UUID.fromString(loan.getReaderId()),
                UUID.fromString(loan.getBookId()),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnedAt(),
                loan.getStatus(),
                loan.getRenewalCount()
        );
    }

    public static Loan toDomain(LoanEntity entity) {
        return Loan.Restore(
                entity.getId().toString(),
                entity.getReaderId().toString(),
                entity.getBookId().toString(),
                entity.getLoanDate(),
                entity.getDueDate(),
                entity.getReturnedAt(),
                entity.getStatus(),
                entity.getRenewalCount()
        );
    }
}
