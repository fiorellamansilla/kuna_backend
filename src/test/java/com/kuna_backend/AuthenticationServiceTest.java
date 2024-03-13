package com.kuna_backend;

import com.kuna_backend.config.MessageStrings;
import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.models.AuthenticationToken;
import com.kuna_backend.models.Client;
import com.kuna_backend.repositories.TokenRepository;
import com.kuna_backend.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class AuthenticationServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private AuthenticationToken createAuthenticationToken(String token, Client client){
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(token);
        authenticationToken.setClient(client);
        return authenticationToken;
    }

    @Test
    public void saveConfirmationToken_WhenValidTokenProvided_ShouldSaveTokenSuccessfully() {

        AuthenticationToken authenticationToken = new AuthenticationToken();
        when(tokenRepository.save(authenticationToken)).thenReturn(authenticationToken);

        authenticationService.saveConfirmationToken(authenticationToken);

        verify(tokenRepository, times(1)).save(authenticationToken);
    }

    @Test
    public void getToken_WhenClientExists_ShouldReturnToken() {

        Client client = new Client();
        AuthenticationToken expectedToken = new AuthenticationToken();
        when(tokenRepository.findTokenByClient(client)).thenReturn(expectedToken);

        AuthenticationToken actualToken = authenticationService.getToken(client);

        assertEquals(expectedToken, actualToken);
        verify(tokenRepository, times(1)).findTokenByClient(client);
    }

    @Test
    public void getClient_WhenTokenExists_ShouldReturnClient() {

        String token = "validToken";
        Client expectedClient = new Client();
        AuthenticationToken authenticationToken = createAuthenticationToken(token, expectedClient);
        when(tokenRepository.findTokenByToken(token)).thenReturn(authenticationToken);

        Client actualClient = authenticationService.getClient(token);

        assertEquals(expectedClient, actualClient);
        verify(tokenRepository, times(1)).findTokenByToken(token);
    }

    @Test
    public void getClient_WhenTokenDoesNotExist_ShouldReturnNull() {

        String token = "invalidToken";
        when(tokenRepository.findTokenByToken(token)).thenReturn(null);

        Client actualClient = authenticationService.getClient(token);

        assertNull(actualClient);
        verify(tokenRepository, times(1)).findTokenByToken(token);
    }

    @Test
    public void authenticateClient_WithValidToken_ShouldNotThrowException() {

        String token = "validToken";
        Client client = new Client();
        AuthenticationToken authenticationToken = createAuthenticationToken(token, client);
        when(tokenRepository.findTokenByToken(token)).thenReturn(authenticationToken);

        assertDoesNotThrow(() -> authenticationService.authenticate(token));

        verify(tokenRepository, times(1)).findTokenByToken(token);
        verify(tokenRepository, never()).findTokenByClient(any(Client.class));
    }

    @Test
    public void authenticateClient_WithNullToken_ShouldThrowAuthenticationFailException() {

        String token = null;

        AuthenticationFailException exception = assertThrows(AuthenticationFailException.class, () -> authenticationService.authenticate(token));

        assertEquals(MessageStrings.AUTH_TOKEN_NOT_PRESENT, exception.getMessage());

        verify(tokenRepository, never()).findTokenByToken(anyString());
        verify(tokenRepository, never()).findTokenByClient(any(Client.class));
    }

    @Test
    public void authenticateClient_WithInvalidToken_ShouldThrowAuthenticationFailException() {

        String token = "invalidToken";
        when(tokenRepository.findTokenByToken(token)).thenReturn(null);

        AuthenticationFailException exception = assertThrows(AuthenticationFailException.class, () -> authenticationService.authenticate(token));

        assertEquals(MessageStrings.AUTH_TOKEN_NOT_VALID, exception.getMessage());

        verify(tokenRepository, times(1)).findTokenByToken(token);
        verify(tokenRepository, never()).findTokenByClient(any(Client.class));
    }
}
