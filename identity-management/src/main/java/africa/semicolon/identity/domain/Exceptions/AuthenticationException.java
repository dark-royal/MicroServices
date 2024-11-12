package africa.semicolon.identity.domain.Exceptions;

public class AuthenticationException extends IdentityManagerException {
    public AuthenticationException(String message) {
        super(message);
    }
}
