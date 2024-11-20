package africa.semicolon.domain.services;



import africa.semicolon.application.port.input.walletUseCases.*;
import africa.semicolon.application.port.output.MonnifyOutputPort;
import africa.semicolon.application.port.output.TransactionOutputPort;
import africa.semicolon.application.port.output.UserOutputPort;
import africa.semicolon.application.port.output.WalletOutputPort;
import africa.semicolon.domain.exceptions.UserNotFoundException;
import africa.semicolon.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.domain.exceptions.WalletNotFoundException;
import africa.semicolon.domain.models.Transaction;
import africa.semicolon.domain.models.User;
import africa.semicolon.domain.models.Wallet;
import africa.semicolon.infrastructure.adapter.monnify.dtos.request.InitializePaymentRequestDto;
import africa.semicolon.infrastructure.adapter.monnify.dtos.request.InitializeTransferRequest;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.InitializeMonnifyTransferResponse;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.InitializePaymentResponseDto;
import africa.semicolon.infrastructure.adapter.persistence.mappers.TransactionPersistenceMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
public class WalletService implements CreateWalletUseCase, FindWalletByIdUsesCase, DepositToWalletUseCase, TransferUseCase, GetAllWalletTransactionByUserId {


    private final WalletOutputPort walletOutputPort;
    private final MonnifyOutputPort monnifyOutputPort;
    private final UserOutputPort userOutputPort;
    private final TransactionOutputPort transactionOutputPort;
    private final TransactionPersistenceMapper transactionPersistenceMapper;


    public WalletService(WalletOutputPort walletOutputPort, MonnifyOutputPort monnifyOutputPort, UserOutputPort userOutputPort, TransactionOutputPort transactionOutputPort, TransactionPersistenceMapper transactionPersistenceMapper) {
        this.walletOutputPort = walletOutputPort;
        this.monnifyOutputPort = monnifyOutputPort;
        this.userOutputPort = userOutputPort;
        this.transactionOutputPort = transactionOutputPort;
        this.transactionPersistenceMapper = transactionPersistenceMapper;
    }

    @Override
    public Wallet createWallet(Wallet wallet) throws WalletAlreadyExistAlreadyException {
        verifyWalletExistence(wallet.getId());
        wallet = walletOutputPort.saveWallet(wallet);

        return wallet;
    }

    @Override
    public Wallet findWalletById(Long id) throws WalletNotFoundException {
        Optional<Wallet> wallet = walletOutputPort.getWalletById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        } else {
            throw new WalletNotFoundException("Wallet not found");
        }
    }

    private void verifyWalletExistence(Long id) throws WalletAlreadyExistAlreadyException {
        if (walletOutputPort.getWalletById(id).isPresent()) {
            throw new WalletAlreadyExistAlreadyException("Wallet exists already");
        }
    }

    @Override
    public void depositToWallet(Wallet wallet, Float amount, Long userId) throws WalletNotFoundException, UserNotFoundException {
        wallet = walletOutputPort.getWalletById(wallet.getId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        User user = userOutputPort.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        InitializePaymentRequestDto initializePaymentDto = InitializePaymentRequestDto.builder()
                .amount(amount)
                .customerEmail(user.getEmail())
                .customerName(user.getFirstName())
                .paymentDescription("Wallet Deposit")
                .currencyCode("NGN")
                .build();

        InitializePaymentResponseDto response = monnifyOutputPort.initializePayment(initializePaymentDto);
        log.info("Monnify response: {}", response);

        if (response != null && response.isRequestSuccessful()) {
            wallet.setBalance(wallet.getBalance().add(BigDecimal.valueOf(amount)));
            walletOutputPort.saveWallet(wallet);
        } else {
            log.warn("Failed to initialize deposit transaction: {}", response.getResponseMessage());
            throw new RuntimeException("Deposit initialization failed. Please try again later.");
        }
    }


    @Override
    public void transfer(Wallet wallet, Float amount, Long userId, String receiverAccountNumber) throws WalletNotFoundException, UserNotFoundException {
        wallet = walletOutputPort.getWalletById(wallet.getId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        User user = userOutputPort.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        InitializeTransferRequest transferRequest = new InitializeTransferRequest();
        transferRequest.setAmount(amount);
        transferRequest.setReceiverAccountNumber(receiverAccountNumber);
        transferRequest.setNarration("Transfer to account");

        InitializeMonnifyTransferResponse transferResponse = monnifyOutputPort.transfer(transferRequest);
        log.info("Monnify transfer response: {}", transferResponse);

        if (transferResponse != null && transferResponse.isRequestSuccessful()) {
            wallet.setBalance(wallet.getBalance().subtract(BigDecimal.valueOf(amount)));
            walletOutputPort.saveWallet(wallet);
        } else {
            log.warn("Failed to initiate transfer: {}", transferResponse.getResponseMessage());
            throw new RuntimeException("Transfer failed. Please try again later.");
        }
    }

    @Override
    public List<Transaction> getAllTransactionsForUser(Long userId) throws UserNotFoundException {
        User user = userOutputPort.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return transactionOutputPort.getAllTransactionById(userId)
                .stream()
                .map(transactionPersistenceMapper::toTransaction)
                .collect(Collectors.toList());

    }

}






