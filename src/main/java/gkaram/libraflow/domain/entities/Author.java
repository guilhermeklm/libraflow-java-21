package gkaram.libraflow.domain.entities;

import gkaram.libraflow.domain.shared.Result;

public class Author {
    private String id;
    private String name;

    private Author(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Result<Author> New(String id, String name) {
        if (name == null || name.isBlank())
            return new Result.Failure<>("Nome do autor não pode ser vazio", Result.ErrorStatus.VALIDATION_ERROR);

        return new Result.Success<>(new Author(id, name));
    }

    public static Author Restore(String id, String name) {
        return new Author(id, name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
