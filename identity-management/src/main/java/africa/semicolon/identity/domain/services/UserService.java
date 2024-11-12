package africa.semicolon.identity.domain.services;

import africa.semicolon.identity.application.port.input.userUseCase.*;
import africa.semicolon.identity.application.port.output.IdentityManagerOutputPort;
import africa.semicolon.identity.application.port.output.SmileIdOutputPort;
import africa.semicolon.identity.application.port.output.UserOutputPort;
import africa.semicolon.identity.domain.Exceptions.*;
import africa.semicolon.identity.domain.models.User;
import africa.semicolon.identity.infrastructure.adapter.KeycloakAdapter;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.request.LoginUserRequest;
import africa.semicolon.identity.infrastructure.adapter.input.rest.dtos.response.LoginUserResponse;
import africa.semicolon.identity.infrastructure.adapter.persistence.mappers.UserPersistenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class UserService implements RegisterUseCase, GetUserUseCase, FindUserUseCase, LoginUseCase, UpdateUserUseCase, DeleteUserUseCase {
    private final UserOutputPort userOutputPort;
    private final KeycloakAdapter keycloakAdapter;
    private final UserPersistenceMapper userPersistenceMapper;
    private final PasswordEncoder passwordEncoder;
    private final SmileIdOutputPort smileIdOutputPort;
    private final IdentityManagerOutputPort identityOutputPort;


    public UserService(UserOutputPort userOutputPort, KeycloakAdapter keycloakAdapter, UserPersistenceMapper userPersistenceMapper, PasswordEncoder passwordEncoder, SmileIdOutputPort smileIdOutputPort, IdentityManagerOutputPort identityOutputPort) {
        this.userOutputPort = userOutputPort;
        this.keycloakAdapter = keycloakAdapter;
        this.userPersistenceMapper = userPersistenceMapper;
        this.passwordEncoder = passwordEncoder;
        this.smileIdOutputPort = smileIdOutputPort;
        this.identityOutputPort = identityOutputPort;
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException, UserNotFoundException {
        verifyUserExistence(user.getEmail());
        keycloakAdapter.createUser(user);
        smileIdOutputPort.verifyPhoneNumber(user.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userOutputPort.saveUser(user);
        log.info("User created with wallet in the database");
        return savedUser;
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        return userOutputPort.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        return userOutputPort.getUserById(id);
    }

    @Override
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) throws InvalidPasswordException, AuthenticationException, UserNotFoundException {
        User user = userOutputPort.getUserByEmail(loginUserRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        log.info("user : {}", user);
        if (!passwordEncoder.matches(loginUserRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid credentials");
        }

        return keycloakAdapter.loginUser(loginUserRequest);
    }

    private void verifyUserExistence(String email) throws UserAlreadyExistsException {
        boolean existByEmail = userOutputPort.existsByEmail(email);
        if (existByEmail) throw new UserAlreadyExistsException("user exists");
    }


    @Override
    public User updateUser(User user) throws UserNotFoundException, PhoneNumberNotFoundException, UserAlreadyExistsException {
        User existinUser = getUserById(user.getId());
        if(!existinUser.getEmail().equals(user.getEmail())){
            throw new UserNotFoundException("user not found");
        }
        if(!existinUser.getPhoneNumber().equals(user.getPhoneNumber())
                && user.getPhoneNumber() != null){
            validatePhoneNumber(existinUser.getPhoneNumber());
            smileIdOutputPort.verifyPhoneNumber(user.getPhoneNumber());
        }
        identityOutputPort.editUser(existinUser.getEmail(), user);
        updateUserFields(user,existinUser);
        return userOutputPort.saveUser(existinUser);


    }

    private void validatePhoneNumber(String phoneNumber) throws UserAlreadyExistsException, PhoneNumberNotFoundException {
        User user = userOutputPort.findByPhoneNumber(phoneNumber);
        if(user != null){
            throw new UserAlreadyExistsException("user exist already");
        }
        else {
            throw new PhoneNumberNotFoundException("phone number not found");
        }
    }

    private void updateUserFields(User existingUser,User user){
        if(existingUser.getFirstName() != null) user.setFirstName(existingUser.getFirstName());
        if(existingUser.getLastName() != null) user.setLastName(existingUser.getLastName());
        if(existingUser.getPhoneNumber() != null) user.setPhoneNumber(existingUser.getPhoneNumber());
        if(existingUser.getEmail() != null) user.setEmail(existingUser.getEmail());
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = userOutputPort.getUserById(id);
        identityOutputPort.deleteUser(user.getEmail());
        userOutputPort.deleteUser(user.getId());
    }
}
