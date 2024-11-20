package africa.semicolon.application.port.input.walletUseCases;

import africa.semicolon.domain.exceptions.UserNotFoundException;
import africa.semicolon.domain.exceptions.WalletNotFoundException;
import africa.semicolon.domain.models.Wallet;

import java.math.BigDecimal;

public interface TransferUseCase {
    void transfer(Wallet wallet, Float amount, Long userId, String receiverAccountNumber) throws WalletNotFoundException, UserNotFoundException;
}
