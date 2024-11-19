package africa.semicolon.identity.infrastructure.adapter;

import africa.semicolon.identity.domain.models.UserEventPayload;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;

public class IdentityPulserProducer {

    private final PulsarClient pulsarClient;
    private final Producer<UserEventPayload> producer;

    public IdentityPulserProducer() throws Exception {
        this.pulsarClient = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();

        this.producer = pulsarClient.newProducer(Schema.JSON(UserEventPayload.class))
                .topic("identity-topic")
                .create();
    }

    public IdentityPulserProducer(PulsarClient pulsarClient, Producer<UserEventPayload> producer) {
        this.pulsarClient = pulsarClient;
        this.producer = producer;
    }

    public void sendMessage(UserEventPayload messageContent) throws Exception {
        producer.send(messageContent);
    }

    public void close() throws Exception {
        producer.close();
        pulsarClient.close();
    }
}