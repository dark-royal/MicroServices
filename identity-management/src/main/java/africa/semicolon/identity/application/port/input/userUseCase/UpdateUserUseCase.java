package africa.semicolon.identity.application.port.input.userUseCase;

import africa.semicolon.identity.domain.Exceptions.PhoneNumberNotFoundException;
import africa.semicolon.identity.domain.Exceptions.UserAlreadyExistsException;
import africa.semicolon.identity.domain.Exceptions.UserNotFoundException;
import africa.semicolon.identity.domain.models.User;

public interface UpdateUserUseCase {
    User updateUser(User user)throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException;
}
