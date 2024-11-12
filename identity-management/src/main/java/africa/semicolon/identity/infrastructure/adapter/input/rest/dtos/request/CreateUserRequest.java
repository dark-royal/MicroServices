package africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private Long id;
    @NotEmpty(message = "name must not be empty")
    private String firstName;
    @NotEmpty(message = "password must not be empty")
    private String lastName;
    private String bvnNumber;
    private String password;
    @NotEmpty(message = "email must not be empty")
    private String email;
    @NotEmpty(message = "phoneNumber must not be empty")
    private String phoneNumber;
    private String role;



}
