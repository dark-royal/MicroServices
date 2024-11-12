package africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindUserRequest {
    private String email;
}
