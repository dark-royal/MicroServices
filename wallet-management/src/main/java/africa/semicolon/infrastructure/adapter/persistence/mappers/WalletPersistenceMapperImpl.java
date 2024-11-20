package africa.semicolon.infrastructure.adapter.persistence.mappers;


import africa.semicolon.domain.models.Wallet;
import africa.semicolon.infrastructure.adapter.persistence.entities.WalletEntity;

public class WalletPersistenceMapperImpl implements WalletPersistenceMapper {
    @Override
    public WalletEntity toWalletEntity(Wallet wallet) {
        return WalletEntity.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();
    }

    @Override
    public Wallet toWallet(WalletEntity walletEntity) {
        return Wallet.builder()
                .id(walletEntity.getId())
                .balance(walletEntity.getBalance())
                .build();
    }
}

