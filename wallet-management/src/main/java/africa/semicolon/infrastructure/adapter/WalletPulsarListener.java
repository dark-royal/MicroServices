package africa.semicolon.infrastructure.adapter;

import africa.semicolon.domain.exceptions.WalletAlreadyExistAlreadyException;
import africa.semicolon.domain.exceptions.WalletNotFoundException;
import africa.semicolon.domain.models.UserEventPayload;
import africa.semicolon.domain.models.Wallet;
import africa.semicolon.domain.services.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;

import java.util.Optional;

@Slf4j
public class WalletPulsarListener {

    private final PulsarClient pulsarClient;
    private final WalletService walletService;
    private Consumer<UserEventPayload> consumer;

    public WalletPulsarListener(PulsarClient pulsarClient, WalletService walletCreationService) {
        this.pulsarClient = pulsarClient;
        this.walletService = walletCreationService;
    }

    public void startListening() {
        try {

            if (pulsarClient.isClosed()) {
                log.error("Pulsar client is already closed");
                return;
            }
            consumer = pulsarClient.newConsumer(Schema.JSON(UserEventPayload.class))
                    .topic("identity-topic")
                    .subscriptionName("wallet-service-subscription")
                    .subscriptionType(SubscriptionType.Exclusive)
                    .subscribe();

            log.info("Pulsar listener started for topic: identity-topic");

            while (true) {
                Message<UserEventPayload> message = consumer.receive();
                try {
                    UserEventPayload userEvent = message.getValue();
                    log.info("Received User Event: {}", userEvent);
                    handleUserEvent(userEvent);

                    consumer.acknowledge(message);
                } catch (Exception e) {
                    log.error("Failed to process message", e);
                    consumer.negativeAcknowledge(message);
                }
            }
        } catch (PulsarClientException e) {
            log.error("Failed to start Pulsar Consumer", e);
        }

    }

    private void handleUserEvent(UserEventPayload userEvent) throws WalletNotFoundException, WalletAlreadyExistAlreadyException {
        Long userId = userEvent.getUserId();
        Optional<Wallet> existingWallet = walletService.findByUserId(userId);

        if (existingWallet.isEmpty()) {
            walletService.createWalletForUser(userId);
            log.info("Wallet created for user ID: {}", userId);
        } else {
            log.info("Wallet already exists for user ID: {}", userId);
        }
    }



}
