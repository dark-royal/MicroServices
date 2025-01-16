package africa.semicolon.application.port.input.savingGoalUseCases;

import africa.semicolon.domain.exceptions.UserNotFoundException;
import africa.semicolon.domain.exceptions.WalletNotFoundException;
import africa.semicolon.domain.models.SavingsGoal;

import java.math.BigDecimal;

public interface CreateSavingGoalUseCase {
    SavingsGoal createSavingsGoal(Long userId, Long walletId,String name, BigDecimal targetAmount, String description) throws WalletNotFoundException, UserNotFoundException;
}
