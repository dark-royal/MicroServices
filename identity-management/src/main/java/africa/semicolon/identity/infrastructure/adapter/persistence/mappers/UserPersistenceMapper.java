package africa.semicolon.identity.infrastructure.adapter.persistence.mappers;

import africa.semicolon.identity.domain.models.User;
import africa.semicolon.identity.infrastructure.adapter.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserPersistenceMapper {

    UserEntity toUserEntity(User user);

    User toUser(UserEntity user);
}