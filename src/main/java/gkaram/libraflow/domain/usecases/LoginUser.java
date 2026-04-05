package gkaram.libraflow.domain.usecases;

import gkaram.libraflow.domain.dto.ErrorOutput;
import gkaram.libraflow.domain.dto.LoginInput;
import gkaram.libraflow.domain.dto.LoginOutput;
import gkaram.libraflow.domain.entities.repository.UserRepository;
import gkaram.libraflow.domain.shared.PasswordHasher;
import gkaram.libraflow.domain.shared.Result;
import gkaram.libraflow.domain.shared.TokenIssuer;

public class LoginUser implements BaseUseCase<LoginInput, LoginOutput> {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenIssuer tokenIssuer;

    public LoginUser(UserRepository userRepository, PasswordHasher passwordHasher, TokenIssuer tokenIssuer) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public LoginOutput execute(LoginInput input) {
        var userOpt = userRepository.findByEmail(input.email());

        if (userOpt.isEmpty()) {
            return new LoginOutput(null, new ErrorOutput("Email ou senha inválidos", Result.ErrorStatus.UNAUTHORIZED.name()));
        }

        var user = userOpt.get();

        if (!passwordHasher.matches(input.password(), user.getPasswordHash())) {
            return new LoginOutput(null, new ErrorOutput("Email ou senha inválidos", Result.ErrorStatus.UNAUTHORIZED.name()));
        }

        String token = tokenIssuer.issue(user.getId(), user.getRole().name());
        return new LoginOutput(token, null);
    }
}
