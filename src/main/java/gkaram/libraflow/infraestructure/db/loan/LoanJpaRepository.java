package gkaram.libraflow.infraestructure.db.loan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanJpaRepository extends JpaRepository<LoanEntity, UUID> {}
