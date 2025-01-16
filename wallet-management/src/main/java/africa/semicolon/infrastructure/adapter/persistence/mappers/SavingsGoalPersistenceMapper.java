package africa.semicolon.infrastructure.adapter.persistence.mappers;

import africa.semicolon.domain.models.SavingsGoal;
import africa.semicolon.infrastructure.adapter.persistence.entities.SavingsGoalEntity;

public interface SavingsGoalPersistenceMapper {
    SavingsGoalEntity toSavingsGoalEntity(SavingsGoal savingsGoal);
    SavingsGoal toSavingsGoal(SavingsGoalEntity savingsGoalEntity);
}
