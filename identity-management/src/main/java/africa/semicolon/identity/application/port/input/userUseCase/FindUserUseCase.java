package africa.semicolon.identity.application.port.input.userUseCase;

import africa.semicolon.identity.domain.Exceptions.UserNotFoundException;
import africa.semicolon.identity.domain.models.User;

public interface FindUserUseCase {

    User findUserByEmail(String email) throws UserNotFoundException;
}
