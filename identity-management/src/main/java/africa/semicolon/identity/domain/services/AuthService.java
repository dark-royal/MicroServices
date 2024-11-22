package africa.semicolon.identity.domain.services;

import africa.semicolon.identity.application.port.input.userUseCase.*;
import africa.semicolon.identity.domain.Exceptions.*;
import africa.semicolon.identity.domain.models.User;
import africa.semicolon.identity.domain.models.UserEventPayload;
import africa.semicolon.identity.infrastructure.adapter.IdentityPulserProducer;
import africa.semicolon.identity.infrastructure.adapter.KeycloakAdapter;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import lombok.AllArgsConstructor;
import org.apache.pulsar.client.api.PulsarClientException;

@AllArgsConstructor
public class AuthService implements RegisterUseCase, LoginUseCase, UpdateUserUseCase, DeleteUserUseCase {

    private final KeycloakAdapter keycloakAdapter;
    private final IdentityPulserProducer identityPulserProducer;


    @Override
    public void deleteUser(String username) throws UserNotFoundException {
        keycloakAdapter.deleteUser(username);
    }

    @Override
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws InvalidPasswordException, AuthenticationException, UserNotFoundException {
        return keycloakAdapter.loginUser(loginUserRequest);
    }

    @Override
    public User createUser(User user) throws Exception {
        try {
            user = keycloakAdapter.createUser(user);
            UserEventPayload eventPayload = new UserEventPayload();
            eventPayload.setUserId(user.getKeycloakId());
            eventPayload.setEmail(user.getEmail());
            eventPayload.setFirstName(user.getFirstName());
            eventPayload.setLastName(user.getLastName());
            eventPayload.setPhoneNumber(user.getPhoneNumber());
            eventPayload.setEventType("USER_CREATED");
            identityPulserProducer.sendMessage(eventPayload);

        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User updateUser(User user) throws Exception {
        try {
            user = keycloakAdapter.editUser(user.getKeycloakId(),user);
            UserEventPayload eventPayload = new UserEventPayload();
            eventPayload.setUserId(user.getKeycloakId());
            eventPayload.setEmail(user.getEmail());
            eventPayload.setFirstName(user.getFirstName());
            eventPayload.setLastName(user.getLastName());
            eventPayload.setPhoneNumber(user.getPhoneNumber());
            eventPayload.setEventType("USER_UPDATED");
            identityPulserProducer.sendMessage(eventPayload);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return user;
    }



}
