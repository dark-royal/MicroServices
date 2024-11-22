package africa.semicolon.infrastructure.adapter.config;

import africa.semicolon.application.port.output.MonnifyOutputPort;
import africa.semicolon.application.port.output.TransactionOutputPort;
import africa.semicolon.application.port.output.UserOutputPort;
import africa.semicolon.application.port.output.WalletOutputPort;
import africa.semicolon.domain.services.WalletService;
import africa.semicolon.infrastructure.adapter.TransactionPersistenceAdapter;
import africa.semicolon.infrastructure.adapter.UserPersistenceAdapter;
import africa.semicolon.infrastructure.adapter.WalletPersistenceAdapter;
import africa.semicolon.infrastructure.adapter.WalletPulsarListener;
import africa.semicolon.infrastructure.adapter.monnify.MonnifyAdapter;
import africa.semicolon.infrastructure.adapter.monnify.rrepository.MonnifyRepository;
import africa.semicolon.infrastructure.adapter.persistence.mappers.*;
import africa.semicolon.infrastructure.adapter.persistence.repositories.TransactionRepository;
import africa.semicolon.infrastructure.adapter.persistence.repositories.UserRepository;
import africa.semicolon.infrastructure.adapter.persistence.repositories.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class BeanConfig {

    @Bean
    public WalletService walletService(WalletOutputPort walletOutputPort,
                                       MonnifyOutputPort monnifyOutputPort,
                                       UserOutputPort userOutputPort,
                                       TransactionOutputPort transactionOutputPort,
                                       TransactionPersistenceMapper transactionPersistenceMapper) {
        return new WalletService(walletOutputPort, monnifyOutputPort, userOutputPort, transactionOutputPort, transactionPersistenceMapper);
    }

    @Bean
    public TransactionPersistenceMapper transactionPersistenceMapper() {
        return new TransactionPersistenceMapperImpl();
    }

    @Bean
    public TransactionOutputPort transactionOutputPort(TransactionRepository transactionRepository,
                                                       TransactionPersistenceMapper transactionPersistenceMapper) {
        return new TransactionPersistenceAdapter(transactionRepository, transactionPersistenceMapper);
    }

    @Bean
    public WalletPulsarListener walletPulsarListener(PulsarClient pulsarClient, WalletService walletCreationService) {
        try {
            return new WalletPulsarListener(pulsarClient, walletCreationService);
        } catch (Exception e) {
            log.error("Error initializing WalletPulsarListener", e);
            throw new RuntimeException("Failed to initialize WalletPulsarListener", e);
        }
    }

    @EventListener(ContextRefreshedEvent.class)
    public void startPulsarListener(ContextRefreshedEvent event) throws Exception {
        WalletPulsarListener walletPulsarListener = event.getApplicationContext().getBean(WalletPulsarListener.class);
        walletPulsarListener.startListening();
    }

    @Bean
    public UserOutputPort userOutputPort(UserRepository userRepository, UserPersistenceMapper userPersistenceMapper) {
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);
    }

    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        return PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();
    }

    @Bean
    public UserPersistenceMapper userPersistenceMapper() {
        return new UserPersistenceMapperImpl();
    }

    @Bean
    public WalletOutputPort walletOutputPort(WalletRepository walletRepository, WalletPersistenceMapper walletPersistenceMapper) {
        return new WalletPersistenceAdapter(walletRepository, walletPersistenceMapper);
    }

    @Bean
    public WalletPersistenceMapper walletPersistenceMapper() {
        return new WalletPersistenceMapperImpl();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MonnifyAdapter monnifyAdapter(RestTemplate restTemplate, MonnifyRepository monnifyRepository) {
        return new MonnifyAdapter(restTemplate, monnifyRepository);
    }
}
