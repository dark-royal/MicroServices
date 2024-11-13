package africa.semicolon.identity.application.port.output;

import africa.semicolon.identity.domain.Exceptions.AuthenticationException;
import africa.semicolon.identity.domain.Exceptions.UserAlreadyExistsException;
import africa.semicolon.identity.domain.Exceptions.UserNotFoundException;
import africa.semicolon.identity.domain.models.User;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import org.keycloak.admin.client.resource.UserResource;

public interface IdentityManagerOutputPort {
    void deleteUser(String id);
    User createUser(User user)throws UserAlreadyExistsException;
    LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws AuthenticationException;
    void assignRole(String userId, String role);
    UserResource getUserById(String userId);
    void forgetPassword(String username) throws UserNotFoundException;
    void sendVerificationEmail();
    User editUser(String keycloakId, User user);

}
