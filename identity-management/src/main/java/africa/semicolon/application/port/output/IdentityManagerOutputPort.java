package africa.semicolon.application.port.output;

import africa.semicolon.domain.Exceptions.AuthenticationException;
import africa.semicolon.domain.Exceptions.UserNotFoundException;
import africa.semicolon.domain.models.User;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import org.keycloak.admin.client.resource.UserResource;

public interface IdentityManagerOutputPort {
    void deleteUser(String id);
    User createUser(User user)throws UserNotFoundException;
    LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws AuthenticationException;
    void assignRole(String userId, String role);
    UserResource getUserById(String userId);
    void forgetPassword(String username) throws UserNotFoundException;
    void sendVerificationEmail();
    void editUser(String keycloakId, User user);

}
