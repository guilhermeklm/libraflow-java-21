package gkaram.libraflow.infraestructure.db.book;

import gkaram.libraflow.infraestructure.db.author.AuthorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    private UUID id;
    private String isbn;
    private String title;
    private String publisher;
    private Integer publicationYear;
    private String genre;
    private Integer totalCopies;
    private Integer availableCopies;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<AuthorEntity> authors;
}
