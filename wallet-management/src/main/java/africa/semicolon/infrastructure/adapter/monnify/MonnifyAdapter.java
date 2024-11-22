package africa.semicolon.infrastructure.adapter.monnify;

import africa.semicolon.application.port.output.MonnifyOutputPort;
import africa.semicolon.infrastructure.adapter.monnify.dtos.request.InitializePaymentRequestDto;
import africa.semicolon.infrastructure.adapter.monnify.dtos.request.InitializeTransferRequest;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.InitializeMonnifyTransferResponse;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.InitializePaymentResponseDto;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.MonnifyAuthenticationResponse;
import africa.semicolon.infrastructure.adapter.monnify.models.MonifyTransaction;
import africa.semicolon.infrastructure.adapter.monnify.rrepository.MonnifyRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;
@AllArgsConstructor
@Slf4j
public class MonnifyAdapter implements MonnifyOutputPort {

    private final  RestTemplate restTemplate;
    private final MonnifyRepository monnifyRepository;

    @Value("${spring.monnify.api.key}")
    private String monnifyApiKey;

    @Value("${spring.monnify.secret.key}")
    private String monnifyApiSecret;

    @Value("${spring.monnify.contract_code}")
    private String contractCode;

    @Value("${spring.monnify.transaction.redirect_url}")
    private String redirectUrl;

    @Value("${spring.monnify.transaction.login_url}")
    private String loginUrl;

    @Value("${spring.monnify.transaction.transfer_url}")
    private String transferUrl;


    @Value("${spring.monnify.base_url}")
    private String baseUrl;
    @Value("${spring.monnify.transaction.initiate_url}")
    private String initiateUrl;

    public MonnifyAdapter(RestTemplate restTemplate, MonnifyRepository monnifyRepository) {
        this.restTemplate = restTemplate;
        this.monnifyRepository = monnifyRepository;
    }


    private MonnifyAuthenticationResponse generateAccessToken() {
        String encodedCredentials = Base64.getEncoder().encodeToString((monnifyApiKey + ":" + monnifyApiSecret).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedCredentials);

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);
        ResponseEntity<MonnifyAuthenticationResponse> response = null;
        try {
            response = restTemplate.postForEntity(loginUrl, entity, MonnifyAuthenticationResponse.class);
            log.info("Access token response: {}", response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Failed to generate access token: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to connect to Monnify: " + e.getMessage(), e);
        }
    }


    private String getAccessToken() {
        MonnifyAuthenticationResponse authResponse = generateAccessToken();
        return authResponse.getResponseBody().getAccessToken();
    }

    private String generateReference() {
        return "TXN-" + UUID.randomUUID();
    }

    @Override
    public InitializePaymentResponseDto initializePayment(InitializePaymentRequestDto initializePaymentDto) {
        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        initializePaymentDto.setContractCode(contractCode);
        initializePaymentDto.setRedirectUrl(redirectUrl);
        initializePaymentDto.setPaymentReference(generateReference());

        HttpEntity<InitializePaymentRequestDto> httpEntity = new HttpEntity<>(initializePaymentDto, headers);
        ResponseEntity<InitializePaymentResponseDto> response = restTemplate.postForEntity(
                baseUrl + initiateUrl,
                httpEntity,
                InitializePaymentResponseDto.class
        );

        if (response.getBody() != null && response.getBody().isRequestSuccessful()) {
            MonifyTransaction transaction = getMonifyTransaction(initializePaymentDto, response);
            monnifyRepository.save(transaction);
        }
        return response.getBody();
    }

    private static MonifyTransaction getMonifyTransaction(InitializePaymentRequestDto initializePaymentDto, ResponseEntity<InitializePaymentResponseDto> response) {
        MonifyTransaction transaction = new MonifyTransaction();
        transaction.setTransactionReference(response.getBody().getResponseBody().getTransactionReference());
        transaction.setPaymentReference(response.getBody().getResponseBody().getPaymentReference());
        transaction.setCustomerName(initializePaymentDto.getCustomerName());
        transaction.setCustomerEmail(initializePaymentDto.getCustomerEmail());
        transaction.setAmount(initializePaymentDto.getAmount());
        transaction.setStatus("INITIALIZED");
        transaction.setCreatedAt(new Date());
        return transaction;
    }

    @Override
    public InitializeMonnifyTransferResponse transfer(InitializeTransferRequest request) {

        String reference = generateReference();
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = getStringHttpEntity(request, reference, headers);
        try {
            ResponseEntity<InitializeMonnifyTransferResponse> responseEntity = restTemplate.postForEntity(
                    baseUrl + transferUrl,
                    entity,
                    InitializeMonnifyTransferResponse.class
            );
            return getInitializeMonnifyTransferResponse(responseEntity);
        } catch (Exception e) {
            InitializeMonnifyTransferResponse transferResponse = new InitializeMonnifyTransferResponse();
            transferResponse.setRequestSuccessful(false);
            transferResponse.setResponseMessage("Failed to initiate transfer: " + e.getMessage());
            transferResponse.setResponseCode("TRANSFER_ERROR");
            return transferResponse;
        }
    }

    private static InitializeMonnifyTransferResponse getInitializeMonnifyTransferResponse(ResponseEntity<InitializeMonnifyTransferResponse> responseEntity) {
        InitializeMonnifyTransferResponse transferResponse = responseEntity.getBody();
        if (transferResponse != null) {
            transferResponse.setRequestSuccessful(true);
        } else {
            transferResponse = new InitializeMonnifyTransferResponse();
            transferResponse.setRequestSuccessful(false);
            transferResponse.setResponseMessage("Transfer response is empty");
            transferResponse.setResponseCode("EMPTY_RESPONSE");
        }
        return transferResponse;
    }

    private static HttpEntity<String> getStringHttpEntity(InitializeTransferRequest request, String reference, HttpHeaders headers) {
        String requestBody = String.format(
                "{ \"amount\": %.2f, " +
                        "\"reference\": \"%s\", " +
                        "\"narration\": \"%s\", " +
                        "\"destinationAccountNumber\": \"%s\", " +
                        "\"destinationBankCode\": \"057\", " +  // Example bank code
                        "\"currency\": \"NGN\", " +
                        "\"sourceAccountNumber\": \"3561571756\" }",  // Your source account number
                request.getAmount(),
                request.getReference(),
                request.getNarration(),
                request.getReceiverAccountNumber()
        );

        return new HttpEntity<>(requestBody, headers);

    }

}
