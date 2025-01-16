package africa.semicolon.application.port.output;


import africa.semicolon.infrastructure.adapter.monnify.dtos.request.InitializePaymentRequestDto;
import africa.semicolon.infrastructure.adapter.monnify.dtos.request.InitializeTransferRequest;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.InitializePaymentResponseDto;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.MonnifyAuthenticationResponse;

public interface MonnifyOutputPort {
    InitializePaymentResponseDto initializePayment(InitializePaymentRequestDto initializePaymentDto);
    MonnifyAuthenticationResponse transfer(InitializeTransferRequest request);
}
