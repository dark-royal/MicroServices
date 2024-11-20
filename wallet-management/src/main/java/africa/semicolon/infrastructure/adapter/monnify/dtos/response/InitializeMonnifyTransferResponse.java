package africa.semicolon.infrastructure.adapter.monnify.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitializeMonnifyTransferResponse {

    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;
    private String transferReference;
    private String transactionStatus;
    private String paymentReference;

}
