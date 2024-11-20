package africa.semicolon.infrastructure.adapter.persistence.repositories;


import africa.semicolon.infrastructure.adapter.persistence.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByUserId(Long userId);

}
