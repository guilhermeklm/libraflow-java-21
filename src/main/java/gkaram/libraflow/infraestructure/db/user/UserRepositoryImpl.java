package gkaram.libraflow.infraestructure.db.user;

import gkaram.libraflow.domain.entities.User;
import gkaram.libraflow.domain.entities.repository.UserRepository;
import gkaram.libraflow.infraestructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserR2dbcRepository userR2dbcRepository;

    public UserRepositoryImpl(UserR2dbcRepository userR2dbcRepository) {
        this.userR2dbcRepository = userR2dbcRepository;
    }

    @Override
    public void createUser(User user) {
        userR2dbcRepository.save(UserMapper.toEntity(user));
    }

    @Override
    public Optional<User> findById(String id) {
        return userR2dbcRepository.findById(UUID.fromString(id)).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userR2dbcRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userR2dbcRepository.findByEmail(email).isPresent();
    }
}
