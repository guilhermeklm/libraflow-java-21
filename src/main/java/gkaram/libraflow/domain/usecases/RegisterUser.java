package gkaram.libraflow.domain.usecases;

import gkaram.libraflow.domain.dto.ErrorOutput;
import gkaram.libraflow.domain.dto.RegisterInput;
import gkaram.libraflow.domain.dto.RegisterOutput;
import gkaram.libraflow.domain.entities.Role;
import gkaram.libraflow.domain.entities.User;
import gkaram.libraflow.domain.entities.repository.UserRepository;
import gkaram.libraflow.domain.shared.PasswordHasher;
import gkaram.libraflow.domain.shared.Result;
import gkaram.libraflow.domain.shared.TokenIssuer;

import java.util.UUID;

public class RegisterUser implements BaseUseCase<RegisterInput, RegisterOutput> {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenIssuer tokenIssuer;

    public RegisterUser(UserRepository userRepository, PasswordHasher passwordHasher, TokenIssuer tokenIssuer) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public RegisterOutput execute(RegisterInput input) {
        if (userRepository.existsByEmail(input.email())) {
            return new RegisterOutput(null, new ErrorOutput("Email já cadastrado", Result.ErrorStatus.CONFLICT.name()));
        }

        String passwordHash = passwordHasher.hash(input.password());

        Result<User> result = User.New(UUID.randomUUID().toString(), input.email(), passwordHash, Role.USER);

        if (result.isFailure()) {
            return new RegisterOutput(null, new ErrorOutput(result.errorMessage(), result.errorStatus().name()));
        }

        userRepository.createUser(result.value());

        String token = tokenIssuer.issue(result.value().getId(), result.value().getRole().name());
        return new RegisterOutput(token, null);
    }
}
