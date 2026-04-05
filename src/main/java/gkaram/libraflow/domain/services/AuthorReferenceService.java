package gkaram.libraflow.domain.services;


import gkaram.libraflow.domain.entities.Author;
import gkaram.libraflow.domain.entities.repository.AuthorRepository;
import gkaram.libraflow.domain.shared.Result;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorReferenceService {

    private final AuthorRepository authorRepository;

    public AuthorReferenceService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Result<List<Author>> resolveAuthors(List<String> ids) {
        List<Author> authors = authorRepository.findByIds(ids);

        if (authors.size() != ids.size()) {
            Set<String> foundIds = authors.stream()
                    .map(Author::getId)
                    .collect(Collectors.toSet());

            List<String> notFoundIds = ids.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            return new Result.Failure<>("Autores não encontrados: " + notFoundIds, Result.ErrorStatus.NOT_FOUND);
        }

        return new Result.Success<>(authors);
    }
}