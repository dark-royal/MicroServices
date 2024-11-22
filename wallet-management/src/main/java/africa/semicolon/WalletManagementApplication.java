package africa.semicolon;

import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WalletManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(WalletManagementApplication.class,args);

    }

}