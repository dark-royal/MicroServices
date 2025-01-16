package africa.semicolon.walletService;

//import africa.semicolon.domain.exceptions.UserNotFoundException;
//import africa.semicolon.domain.exceptions.WalletNotFoundException;
//import africa.semicolon.domain.models.SavingsGoal;
//import africa.semicolon.domain.models.UserEventPayload;
//import africa.semicolon.domain.models.Wallet;
//import africa.semicolon.domain.services.WalletService;
//import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.CreateWalletRequest;
//import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.CreateWalletResponse;
//import africa.semicolon.infrastructure.adapter.monnify.MonnifyAdapter;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//
//import java.math.BigDecimal;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//
//@SpringBootTest
//public class WalletServiceTest {
//
//    @Autowired
//    private WalletService walletService;
//    @Autowired
//    private MonnifyAdapter monnifyAdapter;
//
//    @Test
//    public void testThatWalletCanBeCreated(){
//        UserEventPayload userEventPayload = new UserEventPayload();
//        userEventPayload.setEmail("paul5@gmail.com");
//        userEventPayload.setFirstName("hephzibah");
//        userEventPayload.setLastName("praise");
//        userEventPayload.setPhoneNumber("09023456789");
//        userEventPayload.setUserId(200L);
//        Wallet wallet = new Wallet();
//        wallet.setUserId(userEventPayload.getUserId());
//        wallet.setPin("1234");
//        wallet.setBalance(BigDecimal.ZERO);
//        walletService.createWalletForUser(userEventPayload.getUserId());
//        assertThat(wallet.getUserId()).isEqualTo(userEventPayload.getUserId());
//
//    }
//
//    @Test
//    public void testThatMoneyCanBeDeposited() throws UserNotFoundException, WalletNotFoundException {
//        testThatWalletCanBeCreated();
//        float initialBalance = 0f;
//        float depositAmount = 1000f;
//        Wallet wallet = new Wallet();
//        wallet.setPin("1234");
//        wallet.setId(1L);
//        wallet.setBalance(BigDecimal.valueOf(initialBalance));
//
//        // Set up a user payload
//        UserEventPayload userEventPayload = new UserEventPayload();
//        userEventPayload.setUserId(130L);
//
//        // Perform the deposit operation
//        walletService.depositToWallet(wallet, depositAmount, userEventPayload.getUserId());
//
//        // Assert that the wallet balance is updated correctly
//        assertEquals(initialBalance + depositAmount, wallet.getBalance());
//    }
//
//    //Long userId, String name, BigDecimal targetAmount, String description
//
//    @Test
//    public void testThatUserCanCreateSavingGoal() throws UserNotFoundException, WalletNotFoundException {
//        SavingsGoal savingsGoal = new SavingsGoal();
//        savingsGoal.setUserId(1L);
//        savingsGoal.setWalletId(1L);
//        savingsGoal.setName("Party");
//        savingsGoal.setDescription("Prom party fee");
//        savingsGoal.setTargetAmount(BigDecimal.valueOf(20000.90));
//        walletService.createSavingsGoal(savingsGoal.getUserId(),savingsGoal.getWalletId(),savingsGoal.getName(),savingsGoal.getTargetAmount(),savingsGoal.getDescription());
//        assertNotNull(savingsGoal);
//
//    }
//
//
//}

import africa.semicolon.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.domain.exceptions.WalletNotFoundException;
import africa.semicolon.domain.models.UserEventPayload;
import africa.semicolon.domain.models.Wallet;
import africa.semicolon.domain.services.WalletService;
import africa.semicolon.infrastructure.adapter.WalletPulsarListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
@Slf4j
@SpringBootTest
public class WalletPulsarListenerTest {

    private PulsarClient pulsarClient;
    @Autowired
    private WalletService walletService;
    private WalletPulsarListener walletPulsarListener;

    @BeforeEach
    void setUp() throws PulsarClientException {
        pulsarClient = PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build();
        walletPulsarListener = new WalletPulsarListener(pulsarClient, walletService);
    }

    @Test
    void testHandleUserEvent_walletDoesNotExist() throws Exception {
        Long userId = 100L;
        UserEventPayload userEvent = new UserEventPayload();
        userEvent.setUserId(userId);

        Optional<Wallet> existingWallet = Optional.empty();
        try {
            existingWallet = walletService.findByUserId(userId);
        } catch (WalletNotFoundException e) {
            log.info("Wallet not found for user: {}", userId);
        }

        assertTrue(existingWallet.isEmpty(), "Wallet should not exist for this user.");

        sendMessageToPulsar(userEvent);

        Thread.sleep(1000);

        Optional<Wallet> newWallet = walletService.findByUserId(userId);
        assertTrue(newWallet.isPresent(), "Wallet should have been created for this user.");
    }

    @Test
    void testHandleUserEvent_walletExists() throws Exception {

        Long userId = 123L;
        UserEventPayload userEvent = new UserEventPayload();
        userEvent.setUserId(userId);

        walletService.createWalletForUser(userId);

        sendMessageToPulsar(userEvent);


        Thread.sleep(1000);

        Optional<Wallet> existingWallet = walletService.findByUserId(userId);
        assertTrue(existingWallet.isPresent(), "Wallet should already exist for this user.");
    }

    private void sendMessageToPulsar(UserEventPayload userEvent) throws PulsarClientException {

        Producer<UserEventPayload> producer = pulsarClient.newProducer(Schema.JSON(UserEventPayload.class))
                .topic("identity-topic")
                .create();

        producer.send(userEvent);
        producer.close();
    }

    @Test
    void testStartListening() throws PulsarClientException {
        // Start the listener (this will continuously listen and process messages from Pulsar)
        walletPulsarListener.startListening();

        // In this test, the listener will listen to messages and process them,
        // creating wallets as necessary. You'd typically need a real Pulsar instance
        // and the wallet service to validate that a wallet was created in response.
    }
}

