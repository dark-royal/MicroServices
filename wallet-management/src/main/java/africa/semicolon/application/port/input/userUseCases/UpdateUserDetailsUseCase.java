package africa.semicolon.application.port.input.userUseCases;


import africa.semicolon.domain.exceptions.PhoneNumberNotFoundException;
import africa.semicolon.domain.exceptions.UserAlreadyExistsException;
import africa.semicolon.domain.exceptions.UserNotFoundException;
import africa.semicolon.domain.models.User;

public interface UpdateUserDetailsUseCase {
    User updateUser(User user) throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException;
}
