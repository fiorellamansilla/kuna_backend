package com.kuna_backend.services;

import com.kuna_backend.dtos.ResponseDto;
import com.kuna_backend.dtos.SignupDto;
import com.kuna_backend.exceptions.CustomException;
import com.kuna_backend.models.AuthenticationToken;
import com.kuna_backend.models.Client;
import com.kuna_backend.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
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
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

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

     // Method for encrypting the password
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }

}
