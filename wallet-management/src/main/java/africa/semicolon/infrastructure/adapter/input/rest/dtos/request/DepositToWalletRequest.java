package africa.semicolon.infrastructure.adapter.input.rest.dtos.request;

import africa.semicolon.domain.models.Wallet;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class DepositToWalletRequest {
    private BigDecimal amount;
    private String currency;
    private Long userId;
    private Wallet walletId;
}
