package africa.semicolon.infrastructure.adapter.input.rest;

import africa.semicolon.application.port.input.userUseCase.*;
import africa.semicolon.domain.Exceptions.*;
import africa.semicolon.domain.models.User;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.CreateUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.EditProfileRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.FindUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.CreateUserResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.FindUserResponse;
import africa.semicolon.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import africa.semicolon.infrastructure.adapter.input.rest.mapper.UserRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
public class UserRestAdapter {

    private final FindUserUseCase findUserByEmailUsesCase;
    private final UserRestMapper userRestMapper;
    private final RegisterUseCase registerUseCase;
    private final PasswordEncoder passwordEncoder;
    private final LoginUseCase loginUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserDetailsUseCase;



    public UserRestAdapter(FindUserUseCase findUserUsesCase, UserRestMapper userRestMapper, RegisterUseCase registerUseCase, PasswordEncoder passwordEncoder, LoginUseCase loginUserUseCase, DeleteUserUseCase deleteUserUseCase, UpdateUserUseCase updateUserDetailsUseCase){
        this.findUserByEmailUsesCase = findUserUsesCase;
        this.userRestMapper = userRestMapper;
        this.registerUseCase = registerUseCase;
        this.passwordEncoder = passwordEncoder;
        this.loginUserUseCase = loginUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.updateUserDetailsUseCase = updateUserDetailsUseCase;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<CreateUserResponse> registerUser(@RequestBody @Validated final CreateUserRequest createUserRequest) throws UserAlreadyExistsException,UserNotFoundException {
        User user = userRestMapper.toUser(createUserRequest);
        createUserRequest.setPassword(passwordEncoder.encode(user.getPassword()));
        user = registerUseCase.createUser(user);
        return  new ResponseEntity<>(userRestMapper.tocreateUserResponse(user), HttpStatus.CREATED);
    }


    @GetMapping("/getUser")
    public ResponseEntity<FindUserResponse> findUserByEmail(@RequestParam FindUserRequest findUserRequest) throws UserNotFoundException {
        User user = userRestMapper.toUser(findUserRequest);
        user.setEmail(findUserRequest.getEmail());
        user = findUserByEmailUsesCase.findUserByEmail(user.getEmail());
        return new ResponseEntity<>(userRestMapper.toFindUserResponse(user), HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginUserRequest) throws UserNotFoundException, AuthenticationException, InvalidPasswordException, AuthenticationException, InvalidPasswordException {
        LoginUserResponse loginUserResponse = loginUserUseCase.loginUser(loginUserRequest);
        return new ResponseEntity<>(userRestMapper.toLoginUserResponse(loginUserRequest),HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@PathVariable Long userId ) throws UserNotFoundException {
        deleteUserUseCase.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody EditProfileRequest editProfileRequest) throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException {
        User user = userRestMapper.toUser(editProfileRequest);
        user = updateUserDetailsUseCase.updateUser(user);
        return new ResponseEntity<>(userRestMapper.toEditProfileResponse(user), HttpStatus.OK);


    }

}


