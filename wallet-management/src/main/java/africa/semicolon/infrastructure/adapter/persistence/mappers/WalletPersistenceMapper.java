package africa.semicolon.infrastructure.adapter.persistence.mappers;


import africa.semicolon.domain.models.Wallet;
import africa.semicolon.infrastructure.adapter.persistence.entities.WalletEntity;

public interface WalletPersistenceMapper {
    WalletEntity toWalletEntity(Wallet wallet);
    Wallet toWallet(WalletEntity walletEntity);


}
