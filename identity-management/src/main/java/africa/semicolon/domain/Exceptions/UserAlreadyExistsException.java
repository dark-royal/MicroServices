package africa.semicolon.domain.Exceptions;

public class UserAlreadyExistsException extends IdentityManagerException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
