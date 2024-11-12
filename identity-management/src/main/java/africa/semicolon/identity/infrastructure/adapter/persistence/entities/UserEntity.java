package africa.semicolon.identity.infrastructure.adapter.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String role;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;


}