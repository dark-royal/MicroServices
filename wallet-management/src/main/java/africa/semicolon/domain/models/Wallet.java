package africa.semicolon.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Wallet {
    private Long id;
    private BigDecimal balance;
    private String pin;

}
