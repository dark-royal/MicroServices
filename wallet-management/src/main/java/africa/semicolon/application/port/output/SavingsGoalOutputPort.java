package africa.semicolon.application.port.output;

import africa.semicolon.domain.models.SavingsGoal;

import java.util.List;
import java.util.Optional;

public interface SavingsGoalOutputPort {
    SavingsGoal saveSavingsGoal(SavingsGoal savingsGoal);
    Optional<SavingsGoal> getSavingsGoalById(Long id);
    List<SavingsGoal> getAllSavingsGoalsByUserId(Long userId);
    void deleteSavingsGoal(Long id);
}

