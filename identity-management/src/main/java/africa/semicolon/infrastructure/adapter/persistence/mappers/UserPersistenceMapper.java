package africa.semicolon.infrastructure.adapter.persistence.mappers;

import africa.semicolon.domain.models.User;
import africa.semicolon.infrastructure.adapter.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserPersistenceMapper {

    UserEntity toUserEntity(User user);

    User toUser(UserEntity user);
}