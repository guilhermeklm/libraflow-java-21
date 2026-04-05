package gkaram.libraflow.domain.entities;

import gkaram.libraflow.domain.shared.Result;

import java.util.List;

public class Book {

    private String id;
    private String isbn;
    private String title;
    private List<Author> authors;
    private String publisher;
    private Integer publicationYear;
    private Genre genre;
    private Integer totalCopies;
    private Integer availableCopies;

    private Book(String id, String isbn, String title, List<Author> authors, String publisher, Integer publicationYear, Genre genre, Integer totalCopies, Integer availableCopies) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public static Result<Book> New(
            String id, String isbn, String title, List<Author> authors, String publisher, Integer publicationYear, Genre genre, Integer totalCopies, Integer availableCopies
    ) {
        if (isbn == null || isbn.isBlank())
            return new Result.Failure<>("ISBN não pode ser vazio", Result.ErrorStatus.VALIDATION_ERROR);
        if (title == null || title.isBlank())
            return new Result.Failure<>("Título não pode ser vazio", Result.ErrorStatus.VALIDATION_ERROR);
        if (genre == null)
            return new Result.Failure<>("Gênero não pode ser nulo", Result.ErrorStatus.VALIDATION_ERROR);
        if (totalCopies == null || totalCopies < 0)
            return new Result.Failure<>("Total de cópias inválido", Result.ErrorStatus.VALIDATION_ERROR);
        if (availableCopies == null || availableCopies < 0 || availableCopies > totalCopies)
            return new Result.Failure<>("Cópias disponíveis inválidas", Result.ErrorStatus.VALIDATION_ERROR);

        return new Result.Success<>(new Book(id, isbn, title, authors, publisher, publicationYear, genre, totalCopies, availableCopies));
    }

    public static Book Restore(
            String id, String isbn, String title, List<Author> authors, String publisher, Integer publicationYear, Genre genre, Integer totalCopies, Integer availableCopies
    ) {
        return new Book(id, isbn, title, authors, publisher, publicationYear, genre, totalCopies, availableCopies);
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public Result<Void> returnCopy() {
        if (availableCopies >= totalCopies)
            return new Result.Failure<>("Todas as cópias já estão disponíveis", Result.ErrorStatus.VALIDATION_ERROR);

        this.availableCopies++;
        return new Result.Success<>(null);
    }
}
