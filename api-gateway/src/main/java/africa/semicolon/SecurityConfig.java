package africa.semicolon;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUrl;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
                .csrf(csrfSpec -> csrfSpec.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/users/register", "/api/v1/users/login").permitAll()
                        .anyExchange().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                )
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        return httpSecurity.build();
    }

    @Bean
    public JwtAuthConverter jwtAuthConverter(){
        return new JwtAuthConverter();
    }
}