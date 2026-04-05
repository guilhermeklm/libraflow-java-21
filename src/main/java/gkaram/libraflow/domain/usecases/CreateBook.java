package gkaram.libraflow.domain.usecases;

import gkaram.libraflow.domain.dto.CreateBookInput;
import gkaram.libraflow.domain.dto.CreateBookOutput;
import gkaram.libraflow.domain.dto.ErrorOutput;
import gkaram.libraflow.domain.entities.Author;
import gkaram.libraflow.domain.entities.Book;
import gkaram.libraflow.domain.entities.Genre;
import gkaram.libraflow.domain.entities.repository.BookRepository;
import gkaram.libraflow.domain.services.AuthorReferenceService;
import gkaram.libraflow.domain.shared.Result;

import java.util.List;
import java.util.UUID;

public class CreateBook implements BaseUseCase<CreateBookInput, CreateBookOutput> {

    private final BookRepository bookRepository;
    private final AuthorReferenceService authorReferenceService;

    public CreateBook(
            BookRepository bookRepository,
            AuthorReferenceService authorReferenceService
    ) {
        this.bookRepository = bookRepository;
        this.authorReferenceService = authorReferenceService;
    }

    @Override
    public CreateBookOutput execute(CreateBookInput input) {
        Result<List<Author>> authorsResult = authorReferenceService.resolveAuthors(input.authorIds());

        if (authorsResult.isFailure()) {
            return new CreateBookOutput(null, new ErrorOutput(authorsResult.errorMessage(), authorsResult.errorStatus().name()));
        }

        Result<Book> bookResult = Book.New(
                UUID.randomUUID().toString(),
                input.isbn(),
                input.title(),
                authorsResult.value(),
                input.publisher(),
                input.year(),
                Genre.valueOf(input.genre()),
                input.totalCopies(),
                input.availableCopies()
        );

        if (bookResult.isFailure()) {
            return new CreateBookOutput(null, new ErrorOutput(bookResult.errorMessage(), bookResult.errorStatus().name()));
        }

        bookRepository.createBook(bookResult.value());
        return new CreateBookOutput(bookResult.value().getId(), null);
    }
}
