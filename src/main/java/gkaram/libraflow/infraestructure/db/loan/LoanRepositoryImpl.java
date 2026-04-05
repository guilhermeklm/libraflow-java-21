package gkaram.libraflow.infraestructure.db.loan;

import gkaram.libraflow.domain.entities.Loan;
import gkaram.libraflow.domain.entities.repository.LoanRepository;
import gkaram.libraflow.infraestructure.mapper.LoanMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class LoanRepositoryImpl implements LoanRepository {

    private final LoanJpaRepository loanJpaRepository;

    public LoanRepositoryImpl(LoanJpaRepository loanJpaRepository) {
        this.loanJpaRepository = loanJpaRepository;
    }

    @Override
    public Optional<Loan> findById(String id) {
        return loanJpaRepository.findById(UUID.fromString(id)).map(LoanMapper::toDomain);
    }

    @Override
    public void save(Loan loan) {
        loanJpaRepository.save(LoanMapper.toEntity(loan));
    }
}
