package africa.semicolon.application.port.output;


import africa.semicolon.domain.models.Wallet;

import java.util.Optional;

public interface WalletOutputPort {

    Wallet saveWallet(Wallet wallet);
    Optional<Wallet> getWalletById(Long id);
}
