package africa.semicolon.infrastructure.adapter;

import africa.semicolon.application.port.output.SavingsGoalOutputPort;
import africa.semicolon.domain.models.SavingsGoal;
import africa.semicolon.infrastructure.adapter.persistence.entities.SavingsGoalEntity;
import africa.semicolon.infrastructure.adapter.persistence.entities.WalletEntity;
import africa.semicolon.infrastructure.adapter.persistence.mappers.SavingsGoalPersistenceMapper;
import africa.semicolon.infrastructure.adapter.persistence.repositories.SavingGoalRepository;

import java.util.List;
import java.util.Optional;

public class SavingGoalPersistenceAdapter implements SavingsGoalOutputPort {

    private final SavingsGoalPersistenceMapper savingGoalPersisitenceMapper;
    private final SavingGoalRepository savingGoalRepository;

    public SavingGoalPersistenceAdapter(SavingsGoalPersistenceMapper savingGoalPersisitenceMapper, SavingGoalRepository savingGoalRepository) {
        this.savingGoalPersisitenceMapper = savingGoalPersisitenceMapper;
        this.savingGoalRepository = savingGoalRepository;
    }

    @Override
    public SavingsGoal saveSavingsGoal(SavingsGoal savingsGoal) {
        SavingsGoalEntity savingGoalEntity = savingGoalPersisitenceMapper.toSavingsGoalEntity(savingsGoal);
        savingGoalEntity = savingGoalRepository.save(savingGoalEntity);
        return savingGoalPersisitenceMapper.toSavingsGoal(savingGoalEntity);

    }

    @Override
    public Optional<SavingsGoal> getSavingsGoalById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<SavingsGoal> getAllSavingsGoalsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public void deleteSavingsGoal(Long id) {

    }
}
