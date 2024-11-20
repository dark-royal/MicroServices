package africa.semicolon.application.port.input.TransactionUseCase;


import africa.semicolon.domain.exceptions.UserNotFoundException;
import africa.semicolon.domain.models.Transaction;

import java.util.List;

public interface GetAllTransactionByUserIdUseCase {

    List<Transaction> getAllTransactionByUserId(Long userId) throws UserNotFoundException;

}
