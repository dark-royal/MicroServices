
package africa.semicolon.identity.infrastructure.adapter.persistence.mappers;


import africa.semicolon.identity.domain.models.User;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.EditProfileResponse;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import africa.semicolon.identity.infrastructure.adapter.input.rest.mapper.UserRestMapper;

import java.time.LocalDateTime;

public class UserRestMapperImpl implements UserRestMapper {
    @Override
    public User toUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setFirstName(createUserRequest.getFirstName());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        user.setRole(createUserRequest.getRole());
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        user.setCreatedOn(LocalDateTime.now());
        return user;
    }

    @Override
    public CreateUserResponse tocreateUserResponse(User user) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setFirstName(user.getFirstName());
        createUserResponse.setLastName(user.getLastName());
        createUserResponse.setEmail(user.getEmail());
        createUserResponse.setRole(user.getRole());
        createUserResponse.setPhoneNumber(user.getPhoneNumber());
        createUserResponse.setId(user.getId());
        return createUserResponse;
    }

    @Override
    public User toUser(EditProfileRequest editProfileRequest) {
        User user = new User();
        user.setFirstName(editProfileRequest.getFirstName());
        user.setEmail(editProfileRequest.getEmail());
        user.setLastName(editProfileRequest.getLastName());
        user.setPhoneNumber(editProfileRequest.getPhoneNumber());
        return user;
    }

    @Override
    public EditProfileResponse toEditProfileResponse(User user) {
        EditProfileResponse editProfileResponse = new EditProfileResponse();
        editProfileResponse.setId(user.getId());
        editProfileResponse.setFirstName(user.getFirstName());
        editProfileResponse.setLastName(user.getLastName());
        editProfileResponse.setEmail(user.getEmail());
        editProfileResponse.setPhoneNumber(user.getPhoneNumber());

        return editProfileResponse;
    }


    @Override
    public LoginUserResponse toLoginUserResponse(LoginUserRequest loginUserRequest) {
        return null;
    }
}
