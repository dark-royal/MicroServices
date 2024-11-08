package africa.semicolon.infrastructure.adapter.persistence.mappers;

import africa.semicolon.domain.models.User;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.FindUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.EditProfileResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.FindUserResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import africa.semicolon.infrastructure.adapter.input.rest.mapper.UserRestMapper;

public class UserRestMapperImpl implements UserRestMapper {
    @Override
    public User toUser(CreateUserRequest createUserRequest) {
        return null;
    }

    @Override
    public CreateUserResponse tocreateUserResponse(User user) {
        return null;
    }

    @Override
    public User toUser(EditProfileRequest editProfileRequest) {
        return null;
    }

    @Override
    public EditProfileResponse toEditProfileResponse(User user) {
        return null;
    }

    @Override
    public User toUser(FindUserRequest findUserRequest) {
        return null;
    }

    @Override
    public FindUserResponse toFindUserResponse(User user) {
        return null;
    }

    @Override
    public LoginUserResponse toLoginUserResponse(LoginUserRequest loginUserRequest) {
        return null;
    }
}
