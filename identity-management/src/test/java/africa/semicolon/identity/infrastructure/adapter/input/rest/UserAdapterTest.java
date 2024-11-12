package africa.semicolon.identity.infrastructure.adapter.input.rest;

import africa.semicolon.identity.domain.Exceptions.*;
import africa.semicolon.identity.domain.models.User;
import africa.semicolon.identity.domain.services.UserService;
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
public class UserAdapterTest {
    @Autowired
    private UserService userService;

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
        user = userService.createUser(user);
        log.info("user: {}", user);
        assertThat(user.getId()).isNotNull();
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
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user));
    }


    @Test
    public void testLoginUserSuccess() throws AuthenticationException, UserNotFoundException, InvalidPasswordException {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail("cheist4@gmail.com");
        loginUserRequest.setPassword("tobi");
        LoginUserResponse response1 = userService.loginUser(loginUserRequest);
        assertNotNull(response1.getAccessToken());
        assertNotNull(response1.getRefreshToken());

    }



    @Test
    public void testThatUserCanEditDetails() throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException {
        User user = new User();
        user.setId(1L);
        user.setEmail("cheist4@gmail.com");
        user.setPhoneNumber("09038942436");
        user.setFirstName("Hannah");
        user.setLastName("david");
        User user1 = userService.updateUser(user);
        assertThat(user1).isNotNull();
    }

    @Test
    public void testThatInvalidUserIdCannotEditProfile() throws UserNotFoundException {
        User user = new User();
        user.setId(6L);
        user.setEmail("cheist4@gmail.com");
        user.setPhoneNumber("09038942436");
        user.setFirstName("Hannah");
        user.setLastName("david");
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user));

    }

    @Test
    public void testThatUserCanBeDeleted() throws UserNotFoundException {
        User user = new User();
        user.setId(1L);
        userService.deleteUser(user.getId());
        assertThat(user).isNotNull();

    }

    @Test
    public void testThatUserWithInvalidIdCannotBeDeleted() throws UserNotFoundException {
        User user = new User();
        user.setId(8L);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(user.getId()));

    }

}
