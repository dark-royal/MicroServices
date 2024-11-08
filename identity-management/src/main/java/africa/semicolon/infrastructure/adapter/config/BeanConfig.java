package africa.semicolon.infrastructure.adapter.config;

import africa.semicolon.application.port.output.IdentityManagerOutputPort;
import africa.semicolon.application.port.output.PremblyOutputPort;
import africa.semicolon.application.port.output.UserOutputPort;
import africa.semicolon.domain.services.UserService;
import africa.semicolon.infrastructure.adapter.KeycloakAdapter;
import africa.semicolon.infrastructure.adapter.PremblyAdapter;
import africa.semicolon.infrastructure.adapter.input.rest.mapper.UserRestMapper;
import africa.semicolon.infrastructure.adapter.persistence.UserPersistenceAdapter;
import africa.semicolon.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import africa.semicolon.infrastructure.adapter.persistence.mappers.UserPersistenceMapperImpl;
import africa.semicolon.infrastructure.adapter.persistence.mappers.UserRestMapperImpl;
import africa.semicolon.infrastructure.adapter.persistence.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public UserService userService(UserOutputPort userOutputPort, KeycloakAdapter keycloakAdapter, UserPersistenceMapper userPersistenceMapper, PasswordEncoder passwordEncoder, PremblyOutputPort premblyOutputPort, IdentityManagerOutputPort identityManagerOutputPort) {
        return new UserService(userOutputPort,keycloakAdapter,userPersistenceMapper,passwordEncoder,premblyOutputPort,identityManagerOutputPort);
    }

    @Bean
    public UserPersistenceMapper userPersistenceMapper(){
        return new UserPersistenceMapperImpl();
    }
    @Bean
    public PremblyAdapter premblyAdapter(RestTemplate restTemplate, WebClient.Builder webClientBuilder){
        return  new PremblyAdapter(restTemplate,webClientBuilder);
    }
    @Bean
    public UserRestMapper userRestMapper(){
        return new UserRestMapperImpl();
    }



}
