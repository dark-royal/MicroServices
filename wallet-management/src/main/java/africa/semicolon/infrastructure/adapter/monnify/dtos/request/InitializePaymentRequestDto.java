package africa.semicolon.infrastructure.adapter.monnify.dtos.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class InitializePaymentRequestDto {
    private Float amount;
    private String customerName;
    private String customerEmail;
    private String paymentReference;
    private String paymentDescription;
    private String currencyCode;
    private String contractCode;
    private String redirectUrl;
    private List<String> paymentMethods;


}
//"amount": 100.00,
//  "customerName": "Stephen Ikhane",
//  "customerEmail": "stephen@ikhane.com",
//  "paymentReference": "123031klsadkad",
//  "paymentDescription": "Trial transaction",
//  "currencyCode": "NGN",
//  "contractCode":"32904822812",
//  "redirectUrl": "https://my-merchants-page.com/transaction/confirm",
//  "paymentMethods":["CARD","ACCOUNT_TRANSFER"]
