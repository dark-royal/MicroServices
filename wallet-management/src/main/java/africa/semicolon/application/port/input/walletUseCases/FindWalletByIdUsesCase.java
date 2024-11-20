package africa.semicolon.application.port.input.walletUseCases;


import africa.semicolon.domain.exceptions.WalletNotFoundException;
import africa.semicolon.domain.models.Wallet;

public interface FindWalletByIdUsesCase {
    Wallet findWalletById(Long id) throws WalletNotFoundException;


}
