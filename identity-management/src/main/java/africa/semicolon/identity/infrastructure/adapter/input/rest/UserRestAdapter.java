package africa.semicolon.identity.infrastructure.adapter.input.rest;

import africa.semicolon.identity.domain.Exceptions.*;
import africa.semicolon.identity.domain.models.User;
import africa.semicolon.identity.domain.services.UserService;
import africa.semicolon.identity.application.port.input.userUseCase.*;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.FindUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.FindUserResponse;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import africa.semicolon.identity.infrastructure.adapter.input.rest.mapper.UserRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/users")
public class UserRestAdapter {

    private final FindUserUseCase findUserByEmailUsesCase;
    private final UserRestMapper userRestMapper;
    private final RegisterUseCase registerUseCase;
    private final PasswordEncoder passwordEncoder;
    private final LoginUseCase loginUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserDetailsUseCase;
    private final UserService userService;


    public UserRestAdapter(FindUserUseCase findUserUsesCase, UserRestMapper userRestMapper, RegisterUseCase registerUseCase, PasswordEncoder passwordEncoder, LoginUseCase loginUserUseCase, DeleteUserUseCase deleteUserUseCase, UpdateUserUseCase updateUserDetailsUseCase, UserService userService){
        this.findUserByEmailUsesCase = findUserUsesCase;
        this.userRestMapper = userRestMapper;
        this.registerUseCase = registerUseCase;
        this.passwordEncoder = passwordEncoder;
        this.loginUserUseCase = loginUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.updateUserDetailsUseCase = updateUserDetailsUseCase;
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody @Validated final CreateUserRequest createUserRequest) throws UserAlreadyExistsException, UserNotFoundException {
        try {
            User user = userRestMapper.toUser(createUserRequest);
            createUserRequest.setPassword(passwordEncoder.encode(user.getPassword()));
            user = registerUseCase.createUser(user);
            return new ResponseEntity<>(userRestMapper.tocreateUserResponse(user), HttpStatus.CREATED);
        } catch (IdentityManagerException e) {
            throw new RuntimeException(e.getMessage());

        }
    }


    @GetMapping("/getUser")
    public ResponseEntity<FindUserResponse> findUserByEmail(@RequestParam FindUserRequest findUserRequest) throws UserNotFoundException {
        try {
            User user = userRestMapper.toUser(findUserRequest);
            user.setEmail(findUserRequest.getEmail());
            user = findUserByEmailUsesCase.findUserByEmail(user.getEmail());
            return new ResponseEntity<>(userRestMapper.toFindUserResponse(user), HttpStatus.OK);
        } catch (IdentityManagerException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginUserRequest) throws UserNotFoundException, AuthenticationException, InvalidPasswordException, AuthenticationException, InvalidPasswordException {
        try {
            LoginUserResponse response = userService.loginUser(loginUserRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (IdentityManagerException e) {
            throw new AuthenticationException(e.getMessage());
        }

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id ) throws UserNotFoundException {
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
            user = updateUserDetailsUseCase.updateUser(user);
            return new ResponseEntity<>(userRestMapper.toEditProfileResponse(user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }


    }

}


