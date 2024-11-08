package africa.semicolon.domain.Exceptions;

public class UserNotFoundException extends IdentityManagerException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
