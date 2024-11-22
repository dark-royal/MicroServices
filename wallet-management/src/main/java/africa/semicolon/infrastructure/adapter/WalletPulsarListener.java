package africa.semicolon.infrastructure.adapter;

import africa.semicolon.domain.models.UserEventPayload;
import africa.semicolon.domain.services.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;

@Slf4j
public class WalletPulsarListener {

    private final PulsarClient pulsarClient;
    private final WalletService walletCreationService;
    private Consumer<UserEventPayload> consumer;

    public WalletPulsarListener(PulsarClient pulsarClient, WalletService walletCreationService) {
        this.pulsarClient = pulsarClient;
        this.walletCreationService = walletCreationService;
    }

    public void startListening() {
        try {
            consumer = pulsarClient.newConsumer(Schema.JSON(UserEventPayload.class))
                    .topic("identity-topic")
                    .subscriptionName("wallet-service-subscription")
                    .subscriptionType(SubscriptionType.Exclusive)
                    .subscribe();

            while (true) {
                try {
                    Message<UserEventPayload> message = consumer.receive();
                    UserEventPayload userEvent = message.getValue();
                    log.info("Received user creation event: {}", userEvent);

                    walletCreationService.createWalletForUser(userEvent);
                    consumer.acknowledge(message);
                } catch (Exception e) {
                    log.error("Error while processing user creation event", e);

                }
            }
        } catch (Exception e) {
            log.error("Error initializing Pulsar consumer", e);
        } finally {
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (Exception e) {
                    log.error("Error closing Pulsar consumer", e);
                }
            }
        }
    }


}
