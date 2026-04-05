package gkaram.libraflow.infraestructure.db.author;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorR2dbcRepository extends JpaRepository<AuthorEntity, UUID> {

    List<AuthorEntity> findAllByIdIn(List<UUID> ids);

    Optional<AuthorEntity> findByName(String name);
}
