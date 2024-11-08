package africa.semicolon.application.port.input.userUseCase;

import africa.semicolon.domain.Exceptions.AuthenticationException;
import africa.semicolon.domain.Exceptions.InvalidPasswordException;
import africa.semicolon.domain.Exceptions.UserNotFoundException;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;

public interface LoginUseCase {

    LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws InvalidPasswordException, AuthenticationException, UserNotFoundException;
}
