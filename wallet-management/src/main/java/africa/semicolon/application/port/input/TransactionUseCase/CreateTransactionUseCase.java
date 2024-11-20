package africa.semicolon.application.port.input.TransactionUseCase;


import africa.semicolon.domain.models.Transaction;

public interface CreateTransactionUseCase {
    Transaction createTransaction(Transaction transaction);

}
