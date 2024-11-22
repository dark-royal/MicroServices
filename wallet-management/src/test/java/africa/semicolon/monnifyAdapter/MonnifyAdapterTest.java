package africa.semicolon.monnifyAdapter;

import africa.semicolon.infrastructure.adapter.monnify.MonnifyAdapter;
import africa.semicolon.infrastructure.adapter.monnify.dtos.request.InitializePaymentRequestDto;
import africa.semicolon.infrastructure.adapter.monnify.dtos.request.InitializeTransferRequest;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.InitializeMonnifyTransferResponse;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.InitializePaymentResponseDto;
import africa.semicolon.infrastructure.adapter.monnify.rrepository.MonnifyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class MonnifyAdapterTest {

    @Autowired
    private  MonnifyAdapter monnifyAdapter;


    @Test
    public void testInitializePayment_Successful() {
        InitializePaymentRequestDto requestDto = InitializePaymentRequestDto.builder()
                .amount(1000f)
                .customerName("yoo")
                .customerEmail("praiseoyewole560@gmail.com")
                .paymentReference("unique-payment-ref")
                .paymentDescription("Payment for services")
                .currencyCode("NGN")
                .contractCode("4605783897")
                .redirectUrl("https://my-merchants-page.com/transaction/confirm")
                .paymentMethods(List.of("CARD", "ACCOUNT_TRANSFER"))
                .build();
        InitializePaymentResponseDto result = monnifyAdapter.initializePayment(requestDto);
        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
    }


    @Test
    public void testInitiateTransfer_successful(){
        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        MonnifyRepository mockMonnifyRepository = mock(MonnifyRepository.class);
        MonnifyAdapter monnifyAdapter = new MonnifyAdapter(mockRestTemplate, mockMonnifyRepository);
        InitializeTransferRequest requestDto = new InitializeTransferRequest();
                requestDto.setAmount(1000);
                requestDto.setCurrency("NGN");
                requestDto.setDestinationBankCode("919");
                requestDto.setReceiverAccountNumber("2093500199");
                requestDto.setSourceAccountNumber("3561571756");
                requestDto.setReference("my_reference");
                requestDto.setNarration("Transferred successful");

        InitializeMonnifyTransferResponse result = monnifyAdapter.transfer(requestDto);
        assertNotNull(result);
        assertTrue(result.isRequestSuccessful());
    }

}
