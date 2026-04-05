package gkaram.libraflow.application.controller;

import gkaram.libraflow.domain.dto.CreateAuthorInput;
import gkaram.libraflow.domain.entities.repository.AuthorRepository;
import gkaram.libraflow.domain.usecases.CreateAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final CreateAuthor createAuthor;

    public AuthorController(@Autowired AuthorRepository authorRepository) {
        this.createAuthor = new CreateAuthor(authorRepository);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAuthorInput input) {
        var output = createAuthor.execute(input);

        if (output.error() != null) {
            return ResponseEntity.badRequest().body(output.error());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
}
