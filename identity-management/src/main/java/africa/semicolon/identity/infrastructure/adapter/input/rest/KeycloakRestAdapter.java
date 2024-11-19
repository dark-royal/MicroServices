package africa.semicolon.identity.infrastructure.adapter.input.rest;

import africa.semicolon.identity.domain.Exceptions.*;
import africa.semicolon.identity.domain.models.User;
import africa.semicolon.identity.application.port.input.userUseCase.*;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import africa.semicolon.identity.infrastructure.adapter.input.rest.mapper.UserRestMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class KeycloakRestAdapter {

    private final DeleteUserUseCase deleteUserUseCase;
    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UserRestMapper userRestMapper;

    private final PasswordEncoder passwordEncoder;



    @PostMapping(value = "/register")
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody @Validated final CreateUserRequest createUserRequest) throws UserAlreadyExistsException, UserNotFoundException {
        try {
            User user = userRestMapper.toUser(createUserRequest);
            createUserRequest.setPassword(passwordEncoder.encode(user.getPassword()));
            user = registerUseCase.createUser(user);
            return new ResponseEntity<>(userRestMapper.tocreateUserResponse(user), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new UserAlreadyExistsException(e.getMessage());

        }
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginUserRequest) throws UserNotFoundException, AuthenticationException, InvalidPasswordException, AuthenticationException, InvalidPasswordException {
        try {
            LoginUserResponse response = loginUserUseCase.loginUser(loginUserRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (IdentityManagerException e) {
            throw new AuthenticationException(e.getMessage());
        }

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id ) throws UserNotFoundException {
        try {
            deleteUserUseCase.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IdentityManagerException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody EditProfileRequest editProfileRequest) throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException {
        try {
            User user = userRestMapper.toUser(editProfileRequest);
            user = updateUserUseCase.updateUser(user);
            return new ResponseEntity<>(userRestMapper.toEditProfileResponse(user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}


