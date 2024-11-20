package africa.semicolon.infrastructure.adapter.monnify.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class InitializePaymentResponseDto {

        private boolean requestSuccessful;
        private String responseMessage;
        private String responseCode;
        private ResponseBody responseBody;
        @Getter
        @Setter
        public static class ResponseBody {
            private String transactionReference;
            private String paymentReference;
            private String merchantName;
            private String apiKey;
            private List<String> enabledPaymentMethod;
            private String checkoutUrl;

    }

}
