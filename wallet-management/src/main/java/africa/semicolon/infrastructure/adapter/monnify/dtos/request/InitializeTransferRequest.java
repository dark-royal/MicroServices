package africa.semicolon.infrastructure.adapter.monnify.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitializeTransferRequest {

    private double amount;
    private String reference;
    private String narration;
    private String receiverAccountNumber;
    private String destinationBankCode;
    private String currency;
    private String sourceAccountNumber;

}
