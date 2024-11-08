package africa.semicolon.infrastructure.adapter.persistence.mappers;

import africa.semicolon.domain.models.User;
import africa.semicolon.infrastructure.adapter.persistence.entities.UserEntity;

public class UserPersistenceMapperImpl implements UserPersistenceMapper {
    @Override
    public UserEntity toUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setRole(user.getRole());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setPassword(user.getPassword());


        return userEntity;
    }

    @Override
    public User toUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setRole(userEntity.getRole());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setLastName(userEntity.getLastName());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());


        return user;
    }
}
