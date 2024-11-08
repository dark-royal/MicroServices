package africa.semicolon.infrastructure.adapter.input.rest.mapper;

import africa.semicolon.domain.models.User;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.FindUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.EditProfileResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.FindUserResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;

public interface UserRestMapper {
        User toUser(CreateUserRequest createUserRequest);

        CreateUserResponse tocreateUserResponse(User user);

        User toUser(EditProfileRequest editProfileRequest);
        EditProfileResponse toEditProfileResponse(User user);

        User toUser(FindUserRequest findUserRequest);
        FindUserResponse toFindUserResponse(User user);

        LoginUserResponse toLoginUserResponse(LoginUserRequest loginUserRequest);



    }
