package africa.semicolon.application.port.output;


import africa.semicolon.domain.exceptions.TransactionNotFoundException;
import africa.semicolon.domain.models.Transaction;
import africa.semicolon.infrastructure.adapter.persistence.entities.TransactionEntity;

import java.util.List;

public interface TransactionOutputPort {
    Transaction saveTransaction(Transaction transaction);
   TransactionEntity getTransactionById(Long transactionId) throws TransactionNotFoundException;

    List<TransactionEntity> getAllTransactionById(Long userId);

}
