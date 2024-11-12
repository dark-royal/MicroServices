package africa.semicolon.identity.domain.Exceptions;

public class UserAlreadyExistsException extends IdentityManagerException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
