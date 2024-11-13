package africa.semicolon.identity.domain.services;

import africa.semicolon.identity.application.port.input.userUseCase.*;
import africa.semicolon.identity.domain.Exceptions.*;
import africa.semicolon.identity.domain.models.User;
import africa.semicolon.identity.infrastructure.adapter.KeycloakAdapter;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class AuthService implements RegisterUseCase, LoginUseCase, UpdateUserUseCase, DeleteUserUseCase {

    private final KeycloakAdapter keycloakAdapter;


    @Override
    public void deleteUser(String username) throws UserNotFoundException {
        keycloakAdapter.deleteUser(username);
    }

    @Override
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws InvalidPasswordException, AuthenticationException, UserNotFoundException {
        return keycloakAdapter.loginUser(loginUserRequest);
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException, UserNotFoundException {
        return keycloakAdapter.createUser(user);
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException {
        return keycloakAdapter.editUser(user.getKeycloakId(), user);
    }

//    @Override
//    public User findUserByEmail(String email) throws UserNotFoundException {
//        return keycloakAdapter;
//    }
}
