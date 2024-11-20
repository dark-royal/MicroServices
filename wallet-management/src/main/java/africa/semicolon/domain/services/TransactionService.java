package africa.semicolon.domain.services;


import africa.semicolon.application.port.input.TransactionUseCase.CreateTransactionUseCase;
import africa.semicolon.application.port.input.TransactionUseCase.GetAllTransactionByUserIdUseCase;
import africa.semicolon.application.port.output.TransactionOutputPort;
import africa.semicolon.application.port.output.UserOutputPort;
import africa.semicolon.domain.exceptions.UserNotFoundException;
import africa.semicolon.domain.models.Transaction;
import africa.semicolon.domain.models.User;
import africa.semicolon.infrastructure.adapter.persistence.entities.TransactionEntity;
import africa.semicolon.infrastructure.adapter.persistence.mappers.TransactionPersistenceMapper;
import java.util.List;
public class TransactionService implements CreateTransactionUseCase, GetAllTransactionByUserIdUseCase {

    private final TransactionOutputPort transactionOutputPort;
    private final TransactionPersistenceMapper transactionPersistenceMapper;
    private final UserOutputPort userOutputPort;


    public TransactionService(TransactionOutputPort transactionOutputPort, TransactionPersistenceMapper transactionPersistenceMapper, UserOutputPort userOutputPort) {
        this.transactionOutputPort = transactionOutputPort;
        this.transactionPersistenceMapper = transactionPersistenceMapper;
        this.userOutputPort = userOutputPort;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction = transactionOutputPort.saveTransaction(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> getAllTransactionByUserId(Long userId) throws UserNotFoundException {
       User user = userOutputPort.getUserById(userId);
       if(user == null) {
           throw new UserNotFoundException("user not found");
       }
        List<TransactionEntity> entities =  transactionOutputPort.getAllTransactionById(userId);
        return entities.stream().map(transactionPersistenceMapper::toTransaction).toList();

    }
}