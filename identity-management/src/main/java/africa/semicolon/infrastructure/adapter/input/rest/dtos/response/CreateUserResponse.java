package africa.semicolon.infrastructure.adapter.input.rest.dtos.response;

import africa.semicolon.domain.models.User;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private Long id;
    private User user;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String phoneNumber;



}
