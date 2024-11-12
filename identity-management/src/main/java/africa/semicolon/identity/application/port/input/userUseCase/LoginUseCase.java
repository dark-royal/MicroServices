package africa.semicolon.identity.application.port.input.userUseCase;

import africa.semicolon.identity.domain.Exceptions.AuthenticationException;
import africa.semicolon.identity.domain.Exceptions.InvalidPasswordException;
import africa.semicolon.identity.domain.Exceptions.UserNotFoundException;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;

public interface LoginUseCase {

    LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws InvalidPasswordException, AuthenticationException, UserNotFoundException;
}
