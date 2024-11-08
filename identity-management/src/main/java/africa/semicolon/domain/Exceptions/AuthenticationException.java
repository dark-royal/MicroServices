package africa.semicolon.domain.Exceptions;

public class AuthenticationException extends IdentityManagerException {
    public AuthenticationException(String message) {
        super(message);
    }
}
