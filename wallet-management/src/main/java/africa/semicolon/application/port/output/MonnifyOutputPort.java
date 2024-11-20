package africa.semicolon.application.port.output;


import africa.semicolon.infrastructure.adapter.monnify.dtos.request.InitializePaymentRequestDto;
import africa.semicolon.infrastructure.adapter.monnify.dtos.request.InitializeTransferRequest;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.InitializeMonnifyTransferResponse;
import africa.semicolon.infrastructure.adapter.monnify.dtos.response.InitializePaymentResponseDto;

public interface MonnifyOutputPort {
    InitializePaymentResponseDto initializePayment(InitializePaymentRequestDto initializePaymentDto);
    InitializeMonnifyTransferResponse transfer(InitializeTransferRequest request);
}
