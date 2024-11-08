package africa.semicolon.application.port.input.userUseCase;

import africa.semicolon.domain.Exceptions.UserAlreadyExistsException;
import africa.semicolon.domain.Exceptions.UserNotFoundException;
import africa.semicolon.domain.models.User;

public interface RegisterUseCase {

    User createUser(User user) throws UserAlreadyExistsException, UserNotFoundException;
}
