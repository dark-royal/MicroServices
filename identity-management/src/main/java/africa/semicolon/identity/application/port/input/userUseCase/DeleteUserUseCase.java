package africa.semicolon.identity.application.port.input.userUseCase;

import africa.semicolon.identity.domain.Exceptions.UserNotFoundException;

public interface DeleteUserUseCase {

    void deleteUser(String username) throws UserNotFoundException;
}
