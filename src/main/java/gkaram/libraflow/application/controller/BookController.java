package gkaram.libraflow.application.controller;

import gkaram.libraflow.domain.dto.CreateBookInput;
import gkaram.libraflow.domain.entities.repository.AuthorRepository;
import gkaram.libraflow.domain.entities.repository.BookRepository;
import gkaram.libraflow.domain.services.AuthorReferenceService;
import gkaram.libraflow.domain.usecases.CreateBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final CreateBook createBook;

    public BookController(
            @Autowired BookRepository bookRepository,
            @Autowired AuthorRepository authorRepository
    ) {
        AuthorReferenceService authorReferenceService = new AuthorReferenceService(authorRepository);
        this.createBook = new CreateBook(bookRepository, authorReferenceService);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateBookInput input) {
        var output = createBook.execute(input);

        if (output.error() != null) {
            return ResponseEntity.badRequest().body(output.error());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
}
