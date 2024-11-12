package africa.semicolon.identity.application.port.output;

import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.IdentityVerificationResponse;
import reactor.core.publisher.Mono;

public interface SmileIdOutputPort {
   // Mono<IdentityVerificationResponse> verifyBvnNumber(String bvnNumber);

    Mono<IdentityVerificationResponse> verifyPhoneNumber(String phoneNumber);
}
