package africa.semicolon.application.port.input.userUseCases;


import africa.semicolon.domain.exceptions.UserNotFoundException;
import africa.semicolon.domain.models.User;

public interface GetUserUseCase {
    User getUserById(Long id) throws UserNotFoundException;
}
