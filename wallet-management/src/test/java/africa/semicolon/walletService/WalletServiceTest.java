package africa.semicolon.walletService;

import africa.semicolon.domain.services.WalletService;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.CreateWalletRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.CreateWalletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @Test
    public void testThatWalletCanBeCreated(){
        CreateWalletRequest createWalletRequest = new CreateWalletRequest();
        createWalletRequest.setUserId();
        createWalletRequest.setWalletId();
        createWalletRequest.setBalance(BigDecimal.ZERO);
        CreateWalletResponse response = walletService.createWalletForUser();
        assertThat(response).isNotNull();

    }

}
