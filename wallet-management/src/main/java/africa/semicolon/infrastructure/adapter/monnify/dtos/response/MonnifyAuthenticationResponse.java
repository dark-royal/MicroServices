package africa.semicolon.infrastructure.adapter.monnify.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class MonnifyAuthenticationResponse {
    private String responseCode;
    private String responseMessage;
    private String responseSuccessful;
    private MonnifyAuthResponseBody responseBody;


    @Setter
    @Getter
    public static class MonnifyAuthResponseBody {
        private String accessToken;
        private long expiresIn;
        private String accountNumber;
        private String accountName;
        private String bankName;
        private String bankCode;
        private String accountDurationSeconds;
        private String ussdPayment;
        private String requestTime;
        private String expiresOn;
        private String transactionReference;
        private String paymentReference;
        private BigDecimal amount;
        private int fee;
        private BigDecimal totalPayable;
        private String collectionChannel;
        private String production;



    }
}



