package africa.semicolon.domain.exceptions;

public class PhoneNumberNotFoundException extends WalletException{
    public PhoneNumberNotFoundException(String message) {
        super(message);
    }
}
