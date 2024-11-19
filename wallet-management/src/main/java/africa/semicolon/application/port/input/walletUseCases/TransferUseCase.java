package africa.semicolon.application.port.input.walletUseCases;

import africa.semicolon.domain.models.Wallet;

import java.math.BigDecimal;

public interface WithdrawUseCase {
    void withdrawFromWallet(Wallet wallet, BigDecimal amount, String accountNumber, String bankCode, Long userId) throws Exception;

}
