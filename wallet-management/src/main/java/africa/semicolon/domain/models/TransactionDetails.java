package africa.semicolon.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
public class TransactionDetails {
    private Long transactionId;
    private BigDecimal amount;
    private LocalDateTime date;
    private TransactionType transactionType;
}