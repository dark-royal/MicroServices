package africa.semicolon.domain.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEventPayload {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String eventType;
}
