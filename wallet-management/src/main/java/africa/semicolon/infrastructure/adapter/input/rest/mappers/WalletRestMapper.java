package africa.semicolon.infrastructure.adapter.input.rest.mappers;


import africa.semicolon.domain.models.Wallet;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.CreateWalletRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.DepositToWalletRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.FindWalletRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.CreateWalletResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.DepositToWalletResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.FindWalletResponse;

public interface WalletRestMapper {

    Wallet toWallet(CreateWalletRequest createWalletRequest);

    CreateWalletResponse tocreateWalletResponse(Wallet wallet);

    Wallet toFindWallet(FindWalletRequest findWalletRequest);
    FindWalletResponse tofindWalletResponse(Wallet wallet);

    Wallet toDepositToWallet(DepositToWalletRequest depositToWalletRequest);

    DepositToWalletResponse toDepositToWalletResponse(Wallet wallet);
}
