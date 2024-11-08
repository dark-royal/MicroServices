package africa.semicolon.application.port.input.userUseCase;

import africa.semicolon.domain.Exceptions.UserNotFoundException;

public interface DeleteUserUseCase {

    void deleteUser(Long id) throws UserNotFoundException;
}
