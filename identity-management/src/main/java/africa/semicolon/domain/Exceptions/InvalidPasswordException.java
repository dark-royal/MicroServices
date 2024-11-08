package africa.semicolon.domain.Exceptions;

public class InvalidPasswordException extends IdentityManagerException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
