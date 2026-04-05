package gkaram.libraflow.infraestructure.mapper;

import gkaram.libraflow.domain.entities.Book;
import gkaram.libraflow.domain.entities.Genre;
import gkaram.libraflow.infraestructure.db.author.AuthorEntity;
import gkaram.libraflow.infraestructure.db.book.BookEntity;

import java.util.List;
import java.util.UUID;

public class BookMapper {

    public static BookEntity toEntity(Book book, List<AuthorEntity> authors) {
        return new BookEntity(
                UUID.fromString(book.getId()),
                book.getIsbn(),
                book.getTitle(),
                book.getPublisher(),
                book.getPublicationYear(),
                book.getGenre().name(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                authors
        );
    }

    public static Book toDomain(BookEntity entity) {
        var authors = entity.getAuthors().stream()
                .map(AuthorMapper::toDomain)
                .toList();

        return Book.Restore(
                entity.getId().toString(),
                entity.getIsbn(),
                entity.getTitle(),
                authors,
                entity.getPublisher(),
                entity.getPublicationYear(),
                Genre.valueOf(entity.getGenre()),
                entity.getTotalCopies(),
                entity.getAvailableCopies()
        );
    }
}
