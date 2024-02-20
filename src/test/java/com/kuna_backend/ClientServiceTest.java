package com.kuna_backend;

import com.kuna_backend.dtos.ResponseDto;
import com.kuna_backend.dtos.client.SignInDto;
import com.kuna_backend.dtos.client.SignInResponseDto;
import com.kuna_backend.dtos.client.SignupDto;
import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.exceptions.CustomException;
import com.kuna_backend.models.AuthenticationToken;
import com.kuna_backend.models.Client;
import com.kuna_backend.repositories.ClientRepository;
import com.kuna_backend.services.AuthenticationService;
import com.kuna_backend.services.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signUpWithNonExistingEmail() throws CustomException {

        SignupDto signupDto = new SignupDto("John", "Doe", "john@example.com", "password");

        when(clientRepository.findByEmail(signupDto.getEmail())).thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(new Client());
        doNothing().when(authenticationService).saveConfirmationToken(any(AuthenticationToken.class));

        ResponseDto responseDto = clientService.signUp(signupDto);

        assertNotNull(responseDto);
        assertEquals("success", responseDto.getStatus());
        assertEquals("The user has been successfully created.", responseDto.getMessage());

        verify(clientRepository).findByEmail(signupDto.getEmail());
        verify(clientRepository).save(any(Client.class));
        verify(authenticationService).saveConfirmationToken(any(AuthenticationToken.class));
    }

    @Test
    public void signUpWithExistingEmail() {

        String existingEmail = "existing@example.com";
        Client existingClient = new Client("John", "Doe", existingEmail, "password");

        when(clientRepository.findByEmail(existingEmail)).thenReturn(existingClient);

        SignupDto signupDto = new SignupDto("John", "Doe", existingEmail, "password");

        assertThrows(CustomException.class, () -> clientService.signUp(signupDto));

        verify(clientRepository).findByEmail(signupDto.getEmail());
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    public void signUpWithExceptionOnSave() {

        SignupDto signupDto = new SignupDto("John", "Doe", "john@example.com", "password");

        when(clientRepository.findByEmail(signupDto.getEmail())).thenReturn(null);

        doThrow(new RuntimeException("Failed to save")).when(clientRepository).save(any(Client.class));

        assertThrows(CustomException.class, () -> clientService.signUp(signupDto));

        verify(clientRepository).findByEmail(signupDto.getEmail());
        verify(clientRepository).save(any(Client.class));
        verify(authenticationService, never()).saveConfirmationToken(any(AuthenticationToken.class));
    }

    @Test
    void signInWithValidCredentials() {

        String email = "example@test.com";
        String password = "password_test";

        Client client = new Client();
        client.setEmail(email);
        client.setPassword(passwordEncoder.encode(password));

        SignInDto signInDto = new SignInDto();
        signInDto.setEmail(email);
        signInDto.setPassword(password);

        when(clientRepository.findByEmail(signInDto.getEmail())).thenReturn(client);
        when(passwordEncoder.matches(signInDto.getPassword(), client.getPassword())).thenReturn(true);

        AuthenticationToken token = new AuthenticationToken();
        token.setToken("token");
        when(authenticationService.getToken(client)).thenReturn(token);

        SignInResponseDto response = clientService.signIn(signInDto);

        assertEquals("Success", response.getStatus());
        assertEquals(token.getToken(), response.getToken());

        verify(clientRepository).findByEmail(signInDto.getEmail());
        verify(passwordEncoder).matches(signInDto.getPassword(), client.getPassword());
        verify(authenticationService).getToken(client);
    }

    @Test
    public void signInWithInvalidEmail() {

        // Create a test SignInDto with an invalid email
        SignInDto signInDto = new SignInDto("invalid_email@example.com", "password");

        // Mock the behavior of the clientRepository.findByEmail() method to return null
        when(clientRepository.findByEmail(signInDto.getEmail())).thenReturn(null);

        // Assert that invoking the signIn() method with the test SignInDto should throw an AuthenticationFailException
        assertThrows(AuthenticationFailException.class, () -> clientService.signIn(signInDto));

        // Verify that the clientRepository.findByEmail() method was called with the specified email
        verify(clientRepository).findByEmail(signInDto.getEmail());
    }

    @Test
    public void signInWithInvalidPassword() {

        SignInDto signInDto = new SignInDto("john@example.com", "invalid_password");

        // Create a mock Client object to be returned by the clientRepository.findByEmail() method
        Client mockClient = new Client();
        mockClient.setPassword("$2a$10$OrsbB5HilcA8TubrEm9nCOB9TQ/NPlUhCjSdcBxEnEX9TN1pL7ehG");

        // Mock the behavior of the clientRepository.findByEmail() method to return the mockClient
        when(clientRepository.findByEmail(signInDto.getEmail())).thenReturn(mockClient);

        // Mock the behavior of the passwordEncoder.matches() method to return false
        when(passwordEncoder.matches(signInDto.getPassword(), mockClient.getPassword())).thenReturn(false);

        // Assert that invoking the signIn() method with the test SignInDto should throw an exception
        AuthenticationFailException exception = assertThrows(AuthenticationFailException.class, () -> clientService.signIn(signInDto));

        // Assert the error message of the thrown exception
        assertEquals("This password is invalid. Please, try again.", exception.getMessage());

        // Verify that the clientRepository.findByEmail() method was called with the specified email
        verify(clientRepository).findByEmail(signInDto.getEmail());

        // Verify that the passwordEncoder.matches() method was called with the specified password and client's hashed password
        verify(passwordEncoder).matches(signInDto.getPassword(), mockClient.getPassword());
    }

    @Test
    public void hashPasswordWithBlankPassword() {

        String password = "";

        assertThrows(IllegalArgumentException.class, () -> clientService.hashPassword(password));

    }

    @Test
    public void hashPasswordWithValidPassword() {

        String password = "password";
        String hashedPassword = clientService.hashPassword(password);

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        Assertions.assertNotNull(hashedPassword);
        Assertions.assertTrue(passwordEncoder.matches(password, hashedPassword));
    }

    @Test
    public void getAllClients() {

        when(clientRepository.findAll()).thenReturn(java.util.List.of(new Client(), new Client()));

        List<Client> clients = clientService.getAllClients();

        assertNotNull(clients);
        assertEquals(2, clients.size());
        verify(clientRepository).findAll();
    }

    @Test
    public void getClientWithValidId() {

        Long clientId = 1L;
        Client client = new Client();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Client result = clientService.getClient(clientId);

        assertNotNull(result);
        assertEquals(client, result);
        verify(clientRepository).findById(clientId);
    }

    @Test
    public void getClientWithInvalidId() {

        Long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> clientService.getClient(clientId));

        verify(clientRepository).findById(clientId);
    }

    @Test
    public void deleteClientWithValidId() {

        Long clientId = 1L;

        clientService.deleteClient(clientId);

        verify(clientRepository).deleteById(clientId);
    }

}