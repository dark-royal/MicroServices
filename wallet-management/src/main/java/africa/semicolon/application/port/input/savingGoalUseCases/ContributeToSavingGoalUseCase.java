package africa.semicolon.application.port.input.savingGoalUseCases;

import java.math.BigDecimal;

public interface ContributeToSavingGoalUseCase {
    void contributeToSavingsGoal(Long goalId, BigDecimal amount);
}
