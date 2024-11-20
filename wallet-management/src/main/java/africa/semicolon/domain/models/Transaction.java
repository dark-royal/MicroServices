package africa.semicolon.domain.models;



import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Transaction {
    private Long id;
    private Long walletId;
    private Long userId;
    private String userEmail;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String description;
    private Status status;
    private LocalDateTime createdAt;

}
