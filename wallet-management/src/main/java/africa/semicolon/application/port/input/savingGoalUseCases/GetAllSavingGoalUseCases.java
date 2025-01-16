package africa.semicolon.application.port.input.savingGoalUseCases;

import africa.semicolon.domain.models.SavingsGoal;

import java.util.List;

public interface GetAllSavingGoalUseCases {
    List<SavingsGoal> getAllSavingsGoals(Long userId);
}
