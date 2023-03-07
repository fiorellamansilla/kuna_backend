package com.kuna_backend.services;

import com.kuna_backend.dtos.ResponseDto;
import com.kuna_backend.dtos.client.SignInDto;
import com.kuna_backend.dtos.client.SignInResponseDto;
import com.kuna_backend.dtos.client.SignupDto;
import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.exceptions.CustomException;
import com.kuna_backend.models.AuthenticationToken;
import com.kuna_backend.models.Client;
import com.kuna_backend.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    AuthenticationService authenticationService;

    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    public Client getClient (Integer id) {
        return clientRepository.findById(id).get();
    }

    public void createClient (Client client) {
        clientRepository.save(client);
    }

    public void deleteClient (Integer id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {

        // Check to see if the current email address has already been registered
        if (Objects.nonNull(clientRepository.findByEmail(signupDto.getEmail()))) {
            // We already have a Client with this e-mail account
            throw new CustomException("This client already exists");
        }

        // Encrypt the password
        String encryptedPassword = signupDto.getPassword();
        encryptedPassword = hashPassword(signupDto.getPassword());

        Client client = new Client(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), encryptedPassword);

        Client createdClient;

        try {
            // Save the Client
            createdClient = clientRepository.save(client);
            // Generate a token for Client
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdClient);
            // Save the token in the database
            authenticationService.saveConfirmationToken(authenticationToken);
            // Response to successful registration
            ResponseDto responseDto = new ResponseDto("success", "The Client has been successfully created");
            return responseDto;
        } catch (Exception e) {
            // Handle Error with the Registration
            throw new CustomException(e.getMessage());
        }
    }

    public SignInResponseDto signIn(SignInDto signInDto) {

        // Find Client by Email
        Client client = clientRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(client)) {
            throw new AuthenticationFailException("Client is not valid");
        }

        // Hash the password
        if (!client.getPassword().equals(hashPassword(signInDto.getPassword()))) {
            // If password doesn't match
            throw new AuthenticationFailException("Invalid Password");

        }

        // If the password match
        AuthenticationToken token = authenticationService.getToken(client);

        // Retrieve the token
        if (Objects.isNull(token)) {
            throw new CustomException("Token is not present");
        }
        return new SignInResponseDto("Success", token.getToken());
    }

    // Method for encrypting the password
    public static String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

}
