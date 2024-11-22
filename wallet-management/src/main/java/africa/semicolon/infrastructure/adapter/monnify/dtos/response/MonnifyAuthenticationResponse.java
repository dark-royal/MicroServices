package africa.semicolon.infrastructure.adapter.monnify.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class MonnifyAuthenticationResponse {

    private String requestSuccessful;
    private String responseMessage;
    private String responseCode;
    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AuthenticationResponseBody responseBody;



    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AuthenticationResponseBody {
        @JsonProperty("accessToken")
        private String accessToken;

        @JsonProperty("expiresIn")
        private Long expiresIn;

    }
}
