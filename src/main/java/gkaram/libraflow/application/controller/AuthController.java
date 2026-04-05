package gkaram.libraflow.application.controller;

import gkaram.libraflow.domain.dto.LoginInput;
import gkaram.libraflow.domain.dto.RegisterInput;
import gkaram.libraflow.domain.entities.repository.UserRepository;
import gkaram.libraflow.domain.shared.PasswordHasher;
import gkaram.libraflow.domain.shared.TokenIssuer;
import gkaram.libraflow.domain.usecases.LoginUser;
import gkaram.libraflow.domain.usecases.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUser registerUser;
    private final LoginUser loginUser;

    public AuthController(
            @Autowired UserRepository userRepository,
            @Autowired PasswordHasher passwordHasher,
            @Autowired TokenIssuer tokenIssuer
    ) {
        this.registerUser = new RegisterUser(userRepository, passwordHasher, tokenIssuer);
        this.loginUser = new LoginUser(userRepository, passwordHasher, tokenIssuer);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterInput input) {
        var output = registerUser.execute(input);

        if (output.error() != null) {
            return ResponseEntity.badRequest().body(output.error());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", output.token()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginInput input) {
        var output = loginUser.execute(input);

        if (output.error() != null) {
            return ResponseEntity.badRequest().body(output.error());
        }

        return ResponseEntity.ok(Map.of("token", output.token()));
    }
}
