package africa.semicolon.infrastructure.adapter.input.rest.mappers;


import africa.semicolon.domain.models.User;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.EditProfileResponse;

import java.time.LocalDateTime;

public class UserRestMapperImpl implements UserRestMapper {

    @Override
    public User toUser(CreateUserRequest createUserRequest) {
       User user = new User();
       user.setFirstName(createUserRequest.getName());
       user.setEmail(createUserRequest.getEmail());
       user.setPassword(createUserRequest.getPassword());
       user.setPhoneNumber(createUserRequest.getPhoneNumber());
       user.setWallet(createUserRequest.getWallet());
       user.setCreatedOn(LocalDateTime.now());
        return user;
    }

    @Override
    public CreateUserResponse tocreateUserResponse(User user) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setName(user.getFirstName());
        createUserResponse.setEmail(user.getEmail());
        createUserResponse.setPhoneNumber(user.getPhoneNumber());
        createUserResponse.setId(user.getId());
        createUserResponse.setWallet(user.getWallet());
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

}
