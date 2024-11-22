package africa.semicolon.identity.domain.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEventPayload {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String eventType;
}
