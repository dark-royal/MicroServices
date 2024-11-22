package africa.semicolon.identity.infrastructure.adapter;

import africa.semicolon.identity.domain.models.UserEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.shade.javax.annotation.PreDestroy;

@RequiredArgsConstructor
@Slf4j
public class IdentityPulserProducer {

    private final PulsarClient pulsarClient;

    public void sendMessage(UserEventPayload messageContent) throws Exception {
        Producer<UserEventPayload> producer;
        producer = pulsarClient.newProducer(Schema.JSON(UserEventPayload.class))
                .topic("identity-topic")
                .create();
            try {
        producer.send(messageContent);
        log.info("Message sent successfully for user ID: {}", messageContent.getUserId());
    } catch (Exception e) {
        log.error("Failed to send message", e);
        throw e;
    } finally {
        producer.close();
    }
}

   }
