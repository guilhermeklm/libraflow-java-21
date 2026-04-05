package gkaram.libraflow.domain.entities.repository;

import gkaram.libraflow.domain.entities.User;

import java.util.Optional;

public interface UserRepository {
    void createUser(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
