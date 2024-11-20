package africa.semicolon.application.port.output;

import africa.semicolon.domain.exceptions.UserNotFoundException;
import africa.semicolon.domain.models.User;

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
