package africa.semicolon.infrastructure.adapter.persistence.mappers;

import africa.semicolon.domain.models.SavingsGoal;
import africa.semicolon.infrastructure.adapter.persistence.entities.SavingsGoalEntity;

import java.time.LocalDateTime;

public class SavingsGoalPersistenceMapperImpl implements SavingsGoalPersistenceMapper {

    @Override
    public SavingsGoalEntity toSavingsGoalEntity(SavingsGoal savingsGoal) {
        return SavingsGoalEntity.builder()
                .id(savingsGoal.getId())
                .name(savingsGoal.getName())
                .description(savingsGoal.getDescription())
                .currentAmount(savingsGoal.getCurrentAmount())
                .targetAmount(savingsGoal.getTargetAmount())
                .createdAt(LocalDateTime.now())
                .userId(savingsGoal.getUserId())
                .walletId(savingsGoal.getWalletId())
                .build();
    }


    @Override
    public SavingsGoal toSavingsGoal(SavingsGoalEntity savingsGoalEntity) {
        return SavingsGoal.builder()
                .currentAmount(savingsGoalEntity.getCurrentAmount())
                .targetAmount(savingsGoalEntity.getTargetAmount())
                .name(savingsGoalEntity.getName())
                .description(savingsGoalEntity.getDescription())
                .userId(savingsGoalEntity.getUserId())
                .walletId(savingsGoalEntity.getWalletId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
