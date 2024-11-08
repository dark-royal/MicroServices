package africa.semicolon.domain.models;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String bvnNumber;
    private String keycloakId;
    private String role;
    private LocalDateTime createdOn;
    private String password;
    private String phoneNumber;
}
