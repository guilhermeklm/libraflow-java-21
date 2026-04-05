package gkaram.libraflow.infraestructure.db.author;

import gkaram.libraflow.domain.entities.Author;
import gkaram.libraflow.domain.entities.repository.AuthorRepository;
import gkaram.libraflow.infraestructure.mapper.AuthorMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorR2dbcRepository authorRepo;

    public AuthorRepositoryImpl(AuthorR2dbcRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public List<Author> findByIds(List<String> ids) {
        List<UUID> uuids = ids.stream().map(UUID::fromString).toList();
        return authorRepo.findAllByIdIn(uuids).stream()
                .map(AuthorMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return authorRepo.findByName(name).isPresent();
    }

    @Override
    public void createAuthor(Author author) {
        authorRepo.save(AuthorMapper.toEntity(author));
    }
}
