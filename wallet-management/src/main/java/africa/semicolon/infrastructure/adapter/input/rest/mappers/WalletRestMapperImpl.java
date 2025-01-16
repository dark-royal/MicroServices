package africa.semicolon.infrastructure.adapter.input.rest.mappers;

import africa.semicolon.domain.models.SavingsGoal;
import africa.semicolon.domain.models.Wallet;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.CreateWalletRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.DepositToWalletRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.FindWalletRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.SavingGoalRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.CreateWalletResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.DepositToWalletResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.FindWalletResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.SavingGoalResponse;

import java.time.LocalDateTime;

public class WalletRestMapperImpl implements WalletRestMapper {
    @Override
    public Wallet toWallet(CreateWalletRequest createWalletRequest) {
        Wallet wallet = new Wallet();
        wallet.setId(createWalletRequest.getWalletId());
        wallet.setBalance(createWalletRequest.getBalance());
        wallet.setUserId(createWalletRequest.getUserId());
        return wallet;
    }

    @Override
    public CreateWalletResponse tocreateWalletResponse(Wallet wallet) {
        CreateWalletResponse response = new CreateWalletResponse();
        response.setWalletId(wallet.getId());
        response.setBalance(wallet.getBalance());
        response.setCreatedAt(LocalDateTime.now());
        response.setUserId(wallet.getUserId());
        return response;
    }

    @Override
    public Wallet toFindWallet(FindWalletRequest findWalletRequest) {
        Wallet wallet = new Wallet();
        wallet.setId(findWalletRequest.getId());
        return wallet;
    }

    @Override
    public FindWalletResponse tofindWalletResponse(Wallet wallet) {
        FindWalletResponse response = new FindWalletResponse();
        response.setBalance(wallet.getBalance());
        response.setId(wallet.getId());
        return response;
    }

    @Override
    public Wallet toDepositToWallet(DepositToWalletRequest depositToWalletRequest) {
        Wallet wallet = new Wallet();
        wallet.setId(depositToWalletRequest.getWalletId().getId());
        wallet.setBalance(depositToWalletRequest.getAmount());
        return wallet;
    }

    @Override
    public DepositToWalletResponse toDepositToWalletResponse(Wallet wallet) {
        DepositToWalletResponse response = new DepositToWalletResponse();
        response.setWalletId(wallet.getId());
        //response.setAmount(wallet.getBalance());
        return response;
    }

    @Override
    public SavingsGoal toSave(SavingGoalRequest savingGoalRequest) {
        return null;
    }

    @Override
    public SavingGoalResponse toSavingGoalResponse(SavingsGoal savingsGoal) {
        return null;
    }
}
