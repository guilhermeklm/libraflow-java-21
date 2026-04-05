package gkaram.libraflow.domain.entities.repository;

import gkaram.libraflow.domain.entities.Loan;

import java.util.Optional;

public interface LoanRepository {
    Optional<Loan> findById(String id);
    void save(Loan loan);
}
