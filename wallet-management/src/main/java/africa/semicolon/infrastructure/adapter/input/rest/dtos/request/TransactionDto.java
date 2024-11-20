package africa.semicolon.infrastructure.adapter.input.rest.dtos.request;

import africa.semicolon.domain.models.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
public class TransactionDto {
    private Long userId;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime timestamp;

}
