package africa.semicolon.wallet.infrastructure.adapter.input.rest.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetAllTransactionRequest {
    private Long userId;
}
