package gkaram.libraflow.domain.entities.repository;

import gkaram.libraflow.domain.entities.Book;

import java.util.Optional;

public interface BookRepository {
    void createBook(Book book);
    Optional<Book> findById(String id);
    void update(Book book);
}
