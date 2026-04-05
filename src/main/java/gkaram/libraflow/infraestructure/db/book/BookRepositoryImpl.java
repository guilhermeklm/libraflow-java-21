package gkaram.libraflow.infraestructure.db.book;

import gkaram.libraflow.domain.entities.Book;
import gkaram.libraflow.domain.entities.repository.BookRepository;
import gkaram.libraflow.infraestructure.db.author.AuthorR2dbcRepository;
import gkaram.libraflow.infraestructure.mapper.BookMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookR2dbcRepository bookRepo;
    private final AuthorR2dbcRepository authorRepo;

    public BookRepositoryImpl(BookR2dbcRepository bookRepo, AuthorR2dbcRepository authorRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    @Override
    @Transactional
    public void createBook(Book book) {
        var authorIds = book.getAuthors().stream()
                .map(a -> UUID.fromString(a.getId()))
                .toList();
        var authorEntities = authorRepo.findAllByIdIn(authorIds);
        bookRepo.save(BookMapper.toEntity(book, authorEntities));
    }

    @Override
    public Optional<Book> findById(String id) {
        return bookRepo.findById(UUID.fromString(id)).map(BookMapper::toDomain);
    }

    @Override
    @Transactional
    public void update(Book book) {
        bookRepo.findById(UUID.fromString(book.getId())).ifPresent(entity -> {
            var updated = new BookEntity(
                    entity.getId(),
                    entity.getIsbn(),
                    entity.getTitle(),
                    entity.getPublisher(),
                    entity.getPublicationYear(),
                    entity.getGenre(),
                    book.getTotalCopies(),
                    book.getAvailableCopies(),
                    entity.getAuthors()
            );
            bookRepo.save(updated);
        });
    }
}
