package africa.semicolon.identity.infrastructure.adapter.input.rest;

import africa.semicolon.identity.domain.Exceptions.*;
import africa.semicolon.identity.domain.models.User;
import africa.semicolon.identity.infrastructure.adapter.KeycloakAdapter;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
public class KeycloakAdapterTest {
    @Autowired
    private KeycloakAdapter keycloakAdapter;

    @Test
    public void testThatUserCanBeCreated() throws UserNotFoundException, UserNotFoundException, UserAlreadyExistsException {
        User user = User
                .builder()
                .email("cheist4@gmail.com")
                .firstName("ajiri")
                .lastName("ogeh")
                .password("tobi")
                .role("USER")
                .createdOn(LocalDateTime.now())
                .phoneNumber("09038942436")
                .build();
        user = keycloakAdapter.createUser(user);
        assertThat(user.getEmail()).isEqualTo("cheist4@gmail.com");




    }

    @Test
    public void testThatUserCannotRegisterWithTheSameEmail() throws UserNotFoundException, UserAlreadyExistsException {
        User user = User
                .builder()
                .email("cheist4@gmail.com")
                .firstName("Praise")
                .role("USER")
                .password("password")
            .phoneNumber("09028979349")
                .build();
        assertThrows(UserAlreadyExistsException.class, () -> keycloakAdapter.createUser(user));
    }


    @Test
    public void testLoginUserSuccess() throws AuthenticationException, UserNotFoundException, InvalidPasswordException {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail("cheist4@gmail.com");
        loginUserRequest.setPassword("tobi");
        LoginUserResponse response1 = keycloakAdapter.loginUser(loginUserRequest);
        assertNotNull(response1.getAccessToken());
        assertNotNull(response1.getRefreshToken());

    }



    @Test
    public void testThatUserCanEditDetails() throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException {
        User user = new User();
        user.setEmail("cheist4@gmail.com");
        user.setPhoneNumber("09038942436");
        user.setFirstName("Hannah");
        user.setLastName("david");
        User user1 = keycloakAdapter.editUser(user.getEmail(),user);
        assertThat(user1).isNotNull();
    }

    @Test
    public void testThatInvalidUserIdCannotEditProfile() throws UserNotFoundException {
        User user = new User();
//        user.setId(6L);
        user.setEmail("cheist45@gmail.com");
        user.setPhoneNumber("09038942436");
        user.setFirstName("Hannah");
        user.setLastName("david");
        assertThrows(UserNotFoundException.class, () -> keycloakAdapter.editUser(user.getEmail(),user));

    }

    @Test
    public void testThatUserCanBeDeleted() throws UserNotFoundException {
        User user = new User();
        user.setEmail("chiest4@gamil.com");
        keycloakAdapter.deleteUser(user.getEmail());
        assertThat(user).isNotNull();

    }

    @Test
    public void testThatUserWithInvalidIdCannotBeDeleted() throws UserNotFoundException {
        User user = new User();
        user.setEmail("cheist8@gmail.com");
        assertThrows(UserNotFoundException.class, () -> keycloakAdapter.deleteUser(user.getEmail()));

    }

}
