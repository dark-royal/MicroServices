package africa.semicolon.identity.infrastructure.adapter.config;

import africa.semicolon.identity.application.port.output.UserOutputPort;
import africa.semicolon.identity.domain.services.AuthService;
import africa.semicolon.identity.infrastructure.adapter.IdentityPulserProducer;
import africa.semicolon.identity.infrastructure.adapter.KeycloakAdapter;
import africa.semicolon.identity.infrastructure.adapter.input.rest.mapper.UserRestMapper;
import africa.semicolon.identity.infrastructure.adapter.persistence.UserPersistenceAdapter;
import africa.semicolon.identity.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import africa.semicolon.identity.infrastructure.adapter.persistence.mappers.UserPersistenceMapperImpl;
import africa.semicolon.identity.infrastructure.adapter.persistence.mappers.UserRestMapperImpl;
import africa.semicolon.identity.infrastructure.adapter.persistence.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.PulsarProducerFactory;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeycloakAdapter keycloakAdapter(UserOutputPort userOutputPort, RestTemplate restTemplate, Keycloak keycloak, ObjectMapper objectMapper){
        return new KeycloakAdapter(userOutputPort,restTemplate,keycloak,objectMapper);
    }
    @Bean
    public UserOutputPort userOutputPort(UserRepository userRepository, UserPersistenceMapper userPersistenceMapper) {
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);

    }

    @Bean
    public PulsarTemplate<String> pulsarTemplate(PulsarProducerFactory<String> producerFactory) {
        return new PulsarTemplate<>(producerFactory);
    }



    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    @Bean
    public UserPersistenceMapper userPersistenceMapper(){
        return new UserPersistenceMapperImpl();
    }

    @Bean
    public UserRestMapper userRestMapper(){
        return new UserRestMapperImpl();
    }


@Bean
    public AuthService authService(KeycloakAdapter keycloakAdapter, IdentityPulserProducer pulserProducer){
        return new AuthService(keycloakAdapter,pulserProducer);
}

@Bean
    public IdentityPulserProducer identityPulserProducer() throws Exception {
        return new IdentityPulserProducer();
}
}
