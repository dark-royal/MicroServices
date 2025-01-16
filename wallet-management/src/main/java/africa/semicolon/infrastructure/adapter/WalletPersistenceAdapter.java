package africa.semicolon.infrastructure.adapter;


import africa.semicolon.application.port.output.WalletOutputPort;
import africa.semicolon.domain.exceptions.WalletNotFoundException;
import africa.semicolon.domain.models.User;
import africa.semicolon.domain.models.Wallet;
import africa.semicolon.infrastructure.adapter.persistence.entities.WalletEntity;
import africa.semicolon.infrastructure.adapter.persistence.mappers.WalletPersistenceMapper;
import africa.semicolon.infrastructure.adapter.persistence.repositories.WalletRepository;

import java.util.Optional;

public class WalletPersistenceAdapter implements WalletOutputPort {
    private final WalletRepository walletRepository;
    private final WalletPersistenceMapper walletPersistenceMapper;

    public WalletPersistenceAdapter(WalletRepository walletRepository, WalletPersistenceMapper walletPersistenceMapper) {
        this.walletRepository = walletRepository;
        this.walletPersistenceMapper = walletPersistenceMapper;
    }


    @Override
    public Wallet saveWallet(Wallet wallet) {
        WalletEntity walletEntity = walletPersistenceMapper.toWalletEntity(wallet);
        walletEntity = walletRepository.save(walletEntity);
        return walletPersistenceMapper.toWallet(walletEntity);
    }

    @Override
    public Optional<Wallet> getWalletById(Long id) {
        final Optional<WalletEntity> walletEntity = walletRepository.findById(id);
        return walletEntity.map(walletPersistenceMapper::toWallet);
    }

    @Override
    public Optional<Wallet> findByUserId(Long userId) throws WalletNotFoundException {
        Optional<WalletEntity> walletEntity = walletRepository.findById(userId);

        if (walletEntity.isPresent()) {
            return Optional.of(walletPersistenceMapper.toWallet(walletEntity.get()));
        } else {
            throw new WalletNotFoundException("Wallet not found for user with ID: " + userId);
        }
    }


}
