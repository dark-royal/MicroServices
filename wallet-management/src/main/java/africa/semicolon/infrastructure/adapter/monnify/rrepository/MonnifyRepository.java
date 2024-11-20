package africa.semicolon.infrastructure.adapter.monnify.rrepository;

import africa.semicolon.infrastructure.adapter.monnify.models.MonifyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonnifyRepository extends JpaRepository<MonifyTransaction,Long> {
}
