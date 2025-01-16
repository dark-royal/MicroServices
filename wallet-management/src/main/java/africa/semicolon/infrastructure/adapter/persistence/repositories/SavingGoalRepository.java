package africa.semicolon.infrastructure.adapter.persistence.repositories;

import africa.semicolon.domain.models.SavingsGoal;
import africa.semicolon.infrastructure.adapter.persistence.entities.SavingsGoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingGoalRepository extends JpaRepository<SavingsGoalEntity, Long> {
}
