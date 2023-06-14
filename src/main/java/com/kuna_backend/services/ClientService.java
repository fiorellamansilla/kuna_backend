package com.kuna_backend.services;

import com.kuna_backend.config.MessageStrings;
import com.kuna_backend.dtos.ResponseDto;
import com.kuna_backend.dtos.client.SignInDto;
import com.kuna_backend.dtos.client.SignInResponseDto;
import com.kuna_backend.dtos.client.SignupDto;
import com.kuna_backend.enums.Role;
import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.exceptions.CustomException;
import com.kuna_backend.models.AuthenticationToken;
import com.kuna_backend.models.Client;
import com.kuna_backend.repositories.ClientRepository;
import com.kuna_backend.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.kuna_backend.config.MessageStrings.USER_CREATED;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // Register a Client method
    public ResponseDto signUp(SignupDto signupDto) throws CustomException {
        // Check to see if the current email address has already been registered
        if (Objects.nonNull(clientRepository.findByEmail(signupDto.getEmail()))) {
            // We already have a Client with this e-mail account
            throw new CustomException("This client already exists");
        }

        // Encrypt the password
        String encryptedPassword = hashPassword(signupDto.getPassword());

        Client client = new Client(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), encryptedPassword);

        // Assign a role based on the isEmailAdmin condition

        if (isEmailAdmin(signupDto.getEmail())) {
            client.setRole(Role.ADMIN);
        } else {
            client.setRole(Role.USER);
        }

        Client createdClient;

        try {
            // Save the Client
            createdClient = clientRepository.save(client);
            // Generate a token for Client
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdClient);
            // Save the token in the database
            authenticationService.saveConfirmationToken(authenticationToken);
            // Response to successful registration
            ResponseDto responseDto = new ResponseDto("success", USER_CREATED);
            return responseDto;
        } catch (Exception e) {
            // Handle Error with the Registration
            throw new CustomException(e.getMessage());
        }
    }

    // Login Client method
    public SignInResponseDto signIn(SignInDto signInDto)  {

        // Check if Client exists
        Client client = clientRepository.findByEmail(signInDto.getEmail());
        if (!Helper.notNull(client)){
            throw new AuthenticationFailException("Client is not valid");
        }

        // Compare hashed password stored in database with plaintext password provided during SignIn
        if (!passwordEncoder.matches(signInDto.getPassword(), client.getPassword())) {
            // If password doesn't match
            throw new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
        }
        // If the password match
        AuthenticationToken token = authenticationService.getToken(client);

        // Retrieve the token
        if (!Helper.notNull(token)) {
            throw new CustomException("Token is not present");
        }
        return new SignInResponseDto("Success", token.getToken());
    }


    // Method for hashing the password
    public static String hashPassword(String password) {
        // Check whether the password is null or empty
        if (password.isBlank()) {
            // Throw an error message
            throw new IllegalArgumentException("The password cannot be blank");
        }
        //  This method returns an instance of a 'DelegatingPasswordEncoder' that supports multiple password hashing algorithms,
        //  including 'bcrypt', 'scrypt', and 'argon2' and automatically generates a random salt for each password.
        PasswordEncoder passwordEncoder =  PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // We call the 'encode' method on the 'passwordEncoder' instance to hash the password using the default algorithm ('bcrypt').
        return passwordEncoder.encode(password);
    }

    // Method to check if the email matches the pattern for an admin
    private boolean isEmailAdmin (String email) {

        return email.endsWith("babykuna.com");
    }

    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    public Client getClient (Integer id) {
        return clientRepository.findById(id).get();
    }

    public void deleteClient (Integer id) {
        clientRepository.deleteById(id);
    }
}
