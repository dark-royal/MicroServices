package africa.semicolon.infrastructure.adapter.monnify.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@Entity
@Table(name = "monnify_transaction")
public class MonifyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionReference;
    private String paymentReference;
    private String customerName;
    private String customerEmail;
    private Float amount;
    private String status;
    private Date createdAt;
}
