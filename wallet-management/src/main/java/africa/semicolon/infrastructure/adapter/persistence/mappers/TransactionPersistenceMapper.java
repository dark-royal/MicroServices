package africa.semicolon.infrastructure.adapter.persistence.mappers;

import africa.semicolon.domain.models.Transaction;
import africa.semicolon.infrastructure.adapter.persistence.entities.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionPersistenceMapper {
    TransactionEntity toTransactionEntity(Transaction transaction);
    Transaction toTransaction(TransactionEntity transactionEntity);
}
