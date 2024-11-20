package africa.semicolon.application.port.input.walletUseCases;

import africa.semicolon.domain.exceptions.UserNotFoundException;
import africa.semicolon.domain.models.Transaction;

import java.util.List;

public interface GetAllWalletTransactionByUserId {
    List<Transaction> getAllTransactionsForUser(Long userId)throws UserNotFoundException;
}
