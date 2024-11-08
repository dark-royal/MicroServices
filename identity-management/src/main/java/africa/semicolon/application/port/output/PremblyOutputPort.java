package africa.semicolon.application.port.output;

import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.IdentityVerificationResponse;
import reactor.core.publisher.Mono;

public interface PremblyOutputPort {
    Mono<IdentityVerificationResponse> verifyBvnNumber(String bvnNumber);

    Mono<IdentityVerificationResponse> verifyPhoneNumber(String phoneNumber);
}
