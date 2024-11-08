package africa.semicolon.application.port.input.userUseCase;

import africa.semicolon.domain.Exceptions.PhoneNumberNotFoundException;
import africa.semicolon.domain.Exceptions.UserAlreadyExistsException;
import africa.semicolon.domain.Exceptions.UserNotFoundException;
import africa.semicolon.domain.models.User;

public interface UpdateUserUseCase {
    User updateUser(User user)throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException;
}
