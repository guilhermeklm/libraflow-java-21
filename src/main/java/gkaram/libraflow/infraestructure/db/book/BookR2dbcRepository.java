package gkaram.libraflow.infraestructure.db.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookR2dbcRepository extends JpaRepository<BookEntity, UUID> {
}
