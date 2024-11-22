package africa.semicolon.application.port.input.walletUseCases;


import africa.semicolon.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.domain.models.UserEventPayload;
import africa.semicolon.domain.models.Wallet;

public interface CreateWalletUseCase {
    Wallet createWalletForUser(UserEventPayload userEventPayload) throws WalletAlreadyExistAlreadyException;
}
