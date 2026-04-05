package gkaram.libraflow.domain.usecases;

import gkaram.libraflow.domain.dto.CreateAuthorInput;
import gkaram.libraflow.domain.dto.CreateAuthorOutput;
import gkaram.libraflow.domain.dto.ErrorOutput;
import gkaram.libraflow.domain.entities.Author;
import gkaram.libraflow.domain.entities.repository.AuthorRepository;
import gkaram.libraflow.domain.shared.Result;

import java.util.UUID;

public class CreateAuthor implements BaseUseCase<CreateAuthorInput, CreateAuthorOutput> {

    private final AuthorRepository authorRepository;

    public CreateAuthor(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public CreateAuthorOutput execute(CreateAuthorInput input) {
        if (authorRepository.existsByName(input.name())) {
            return new CreateAuthorOutput(null, new ErrorOutput("Autor com este nome já existe", Result.ErrorStatus.CONFLICT.name()));
        }

        Result<Author> result = Author.New(UUID.randomUUID().toString(), input.name());

        if (result.isFailure()) {
            return new CreateAuthorOutput(null, new ErrorOutput(result.errorMessage(), result.errorStatus().name()));
        }

        authorRepository.createAuthor(result.value());
        return new CreateAuthorOutput(result.value().getId(), null);
    }
}
