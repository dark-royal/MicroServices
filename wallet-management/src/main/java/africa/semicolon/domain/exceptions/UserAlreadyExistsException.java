package africa.semicolon.domain.exceptions;

public class UserAlreadyExistsException extends WalletException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
