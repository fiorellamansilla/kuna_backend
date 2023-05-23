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
    public void signUp_WithNonExistingEmail_ShouldRegisterClientAndReturnSuccessResponse() throws CustomException {

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
    public void signUp_WithExistingEmail_ShouldThrowCustomException() {

        String existingEmail = "existing@example.com";
        Client existingClient = new Client("John", "Doe", existingEmail, "password");

        when(clientRepository.findByEmail(existingEmail)).thenReturn(existingClient);

        SignupDto signupDto = new SignupDto("John", "Doe", existingEmail, "password");

        assertThrows(CustomException.class, () -> clientService.signUp(signupDto));

        verify(clientRepository).findByEmail(signupDto.getEmail());
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    public void signUp_WithExceptionOnSave_ShouldThrowCustomException() {

        SignupDto signupDto = new SignupDto("John", "Doe", "john@example.com", "password");

        when(clientRepository.findByEmail(signupDto.getEmail())).thenReturn(null);

        doThrow(new RuntimeException("Failed to save")).when(clientRepository).save(any(Client.class));

        assertThrows(CustomException.class, () -> clientService.signUp(signupDto));

        verify(clientRepository).findByEmail(signupDto.getEmail());
        verify(clientRepository).save(any(Client.class));
        verify(authenticationService, never()).saveConfirmationToken(any(AuthenticationToken.class));
    }

    @Test
    void signIn_WithValidCredentials_ReturnsToken() {

        // Arrange
        String email = "test@example.com";
        String password = "password";
        String hashedPassword = passwordEncoder.encode(password);

        // Mock the input data
        Client client = new Client();
        client.setEmail(email);
        client.setPassword(hashedPassword); // Mock the hashed password

        SignInDto signInDto = new SignInDto(email, password);

        when(clientRepository.findByEmail(signInDto.getEmail())).thenReturn(client);
        when(passwordEncoder.matches(signInDto.getPassword(), client.getPassword())).thenReturn(true);

        // Mock the authentication token retrieval from authentication service
        AuthenticationToken token = new AuthenticationToken();
        token.setToken("test_token");

        when(authenticationService.getToken(client)).thenReturn(token);

        // Act
        SignInResponseDto responseDto = clientService.signIn(signInDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("Success", responseDto.getStatus());
        assertEquals("test_token", responseDto.getToken());

        // Verify that the client repository method and password encoder method were called
        verify(clientRepository).findByEmail(signInDto.getEmail());
        verify(passwordEncoder).matches(signInDto.getPassword(), client.getPassword());

        // Verify that the authentication service method was called
        verify(authenticationService).getToken(client);
    }

    @Test
    public void signIn_WithInvalidEmail_ShouldThrowAuthenticationFailException() {

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
    public void signIn_WithInvalidPassword_ShouldThrowAuthenticationFailException() {

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
    public void hashPassword_WithBlankPassword_ShouldThrowIllegalArgumentException() {
        // Arrange
        String password = "";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> clientService.hashPassword(password));
    }

    @Test
    public void hashPassword_WithValidPassword_ShouldReturnHashedPassword() {

        String password = "password";
        String hashedPassword = clientService.hashPassword(password);

        Assertions.assertNotNull(hashedPassword);
        Assertions.assertTrue(PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(password, hashedPassword));
    }

    @Test
    public void getAllClients_ShouldReturnListOfClients() {
        // Arrange
        when(clientRepository.findAll()).thenReturn(java.util.List.of(new Client(), new Client()));

        // Act
        List<Client> clients = clientService.getAllClients();

        // Assert
        assertNotNull(clients);
        assertEquals(2, clients.size());
        verify(clientRepository).findAll();
    }

    @Test
    public void getClient_WithValidId_ShouldReturnClient() {
        // Arrange
        int clientId = 1;
        Client client = new Client();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        // Act
        Client result = clientService.getClient(clientId);

        // Assert
        assertNotNull(result);
        assertEquals(client, result);
        verify(clientRepository).findById(clientId);
    }

    @Test
    public void getClient_WithInvalidId_ShouldThrowNoSuchElementException() {
        // Arrange
        int clientId = 1;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> clientService.getClient(clientId));
        verify(clientRepository).findById(clientId);
    }

    @Test
    public void deleteClient_WithValidId_ShouldDeleteClient() {
        // Arrange
        int clientId = 1;

        // Act
        clientService.deleteClient(clientId);

        // Assert
        verify(clientRepository).deleteById(clientId);
    }

}