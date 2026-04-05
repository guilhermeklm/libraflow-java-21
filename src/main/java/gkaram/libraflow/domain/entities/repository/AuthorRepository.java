package gkaram.libraflow.domain.entities.repository;

import gkaram.libraflow.domain.entities.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> findByIds(List<String> ids);
    boolean existsByName(String name);
    void createAuthor(Author author);
}
