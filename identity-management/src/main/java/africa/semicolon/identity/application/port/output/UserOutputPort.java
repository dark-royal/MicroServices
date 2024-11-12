package africa.semicolon.identity.application.port.output;

import africa.semicolon.identity.domain.Exceptions.UserNotFoundException;
import africa.semicolon.identity.domain.models.User;

import java.util.Optional;

public interface UserOutputPort {
    User saveUser(User user);
    Optional<User> getUserByEmail(String email);
    User getUserById(Long id) throws UserNotFoundException;

    boolean existsByEmail(String email);

    boolean existById(Long userId);
    void deleteUser(Long userId);

    User findByPhoneNumber(String phoneNumber);

}
