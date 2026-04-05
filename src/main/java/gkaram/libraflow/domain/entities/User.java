package gkaram.libraflow.domain.entities;

import gkaram.libraflow.domain.shared.Result;

public class User {

    private String id;
    private String email;
    private String passwordHash;
    private Role role;

    private User(String id, String email, String passwordHash, Role role) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public static Result<User> New(String id, String email, String passwordHash, Role role) {
        if (email == null || email.isBlank())
            return new Result.Failure<>("Email não pode ser vazio", Result.ErrorStatus.VALIDATION_ERROR);
        if (!email.contains("@"))
            return new Result.Failure<>("Email inválido", Result.ErrorStatus.VALIDATION_ERROR);
        if (passwordHash == null || passwordHash.isBlank())
            return new Result.Failure<>("Senha não pode ser vazia", Result.ErrorStatus.VALIDATION_ERROR);
        if (role == null)
            return new Result.Failure<>("Role não pode ser nula", Result.ErrorStatus.VALIDATION_ERROR);

        return new Result.Success<>(new User(id, email, passwordHash, role));
    }

    public static User Restore(String id, String email, String passwordHash, Role role) {
        return new User(id, email, passwordHash, role);
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }
}
