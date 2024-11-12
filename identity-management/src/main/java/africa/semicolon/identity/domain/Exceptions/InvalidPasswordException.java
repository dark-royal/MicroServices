package africa.semicolon.identity.domain.Exceptions;

public class InvalidPasswordException extends IdentityManagerException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
