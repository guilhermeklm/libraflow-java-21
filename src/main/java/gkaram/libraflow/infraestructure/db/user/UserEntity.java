package gkaram.libraflow.infraestructure.db.user;

import gkaram.libraflow.domain.entities.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;
    private String email;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;
}
