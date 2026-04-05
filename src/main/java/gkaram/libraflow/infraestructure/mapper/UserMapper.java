package gkaram.libraflow.infraestructure.mapper;

import gkaram.libraflow.domain.entities.User;
import gkaram.libraflow.infraestructure.db.user.UserEntity;

import java.util.UUID;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        return new UserEntity(
                UUID.fromString(user.getId()),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole()
        );
    }

    public static User toDomain(UserEntity entity) {
        return User.Restore(
                entity.getId().toString(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getRole()
        );
    }
}
