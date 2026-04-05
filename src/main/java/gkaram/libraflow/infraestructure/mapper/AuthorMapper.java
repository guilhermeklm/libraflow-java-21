package gkaram.libraflow.infraestructure.mapper;

import gkaram.libraflow.domain.entities.Author;
import gkaram.libraflow.infraestructure.db.author.AuthorEntity;

import java.util.UUID;

public class AuthorMapper {

    public static AuthorEntity toEntity(Author author) {
        return new AuthorEntity(
                UUID.fromString(author.getId()),
                author.getName()
        );
    }

    public static Author toDomain(AuthorEntity entity) {
        return Author.Restore(
                entity.getId().toString(),
                entity.getName()
        );
    }
}
