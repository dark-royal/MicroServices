package africa.semicolon.identity.application.port.input.userUseCase;

import africa.semicolon.identity.domain.Exceptions.UserAlreadyExistsException;
import africa.semicolon.identity.domain.Exceptions.UserNotFoundException;
import africa.semicolon.identity.domain.models.User;

public interface RegisterUseCase {

    User createUser(User user) throws UserAlreadyExistsException, UserNotFoundException;
}
