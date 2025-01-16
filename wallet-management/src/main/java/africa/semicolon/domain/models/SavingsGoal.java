package africa.semicolon.domain.models;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingsGoal {
    private Long id;
    private Long userId;
    private Long walletId;
    private LocalDateTime createdAt;
    private String name;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private String description;
    private boolean isAchieved;


}

