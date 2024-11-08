package africa.semicolon.application.port.input.userUseCase;

import africa.semicolon.domain.Exceptions.UserNotFoundException;
import africa.semicolon.domain.models.User;

public interface FindUserUseCase {

    User findUserByEmail(String email) throws UserNotFoundException;
}
