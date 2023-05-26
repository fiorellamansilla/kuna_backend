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
    private TokenRepository repository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveConfirmationToken() {
        AuthenticationToken authenticationToken = new AuthenticationToken();
        when(repository.save(authenticationToken)).thenReturn(authenticationToken);

        assertDoesNotThrow(() -> authenticationService.saveConfirmationToken(authenticationToken));

        verify(repository, times(1)).save(authenticationToken);
    }

    @Test
    public void testGetToken() {
        Client client = new Client();
        AuthenticationToken expectedToken = new AuthenticationToken();
        when(repository.findTokenByClient(client)).thenReturn(expectedToken);

        AuthenticationToken actualToken = authenticationService.getToken(client);

        assertEquals(expectedToken, actualToken);
        verify(repository, times(1)).findTokenByClient(client);
    }

    @Test
    public void testGetClientWhenTokenExists() {
        String token = "validToken";
        Client expectedClient = new Client();

        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(token);
        authenticationToken.setClient(expectedClient);

        when(repository.findTokenByToken(token)).thenReturn(authenticationToken);

        Client actualClient = authenticationService.getClient(token);

        assertEquals(expectedClient, actualClient);
        verify(repository, times(1)).findTokenByToken(token);
    }

    @Test
    public void testGetClientWhenTokenDoesNotExist() {
        String token = "invalidToken";

        when(repository.findTokenByToken(token)).thenReturn(null);

        Client actualClient = authenticationService.getClient(token);

        assertNull(actualClient);
        verify(repository, times(1)).findTokenByToken(token);
    }

    @Test
    public void testAuthenticateWithValidToken() {
        String token = "validToken";
        Client client = new Client();

        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(token);
        authenticationToken.setClient(client);

        when(repository.findTokenByToken(token)).thenReturn(authenticationToken);

        assertDoesNotThrow(() -> authenticationService.authenticate(token));

        verify(repository, times(1)).findTokenByToken(token);
        verify(repository, never()).findTokenByClient(any(Client.class));
    }

    @Test
    public void testAuthenticateWithNullToken() {
        String token = null;

        AuthenticationFailException exception = assertThrows(AuthenticationFailException.class, () -> authenticationService.authenticate(token));

        assertEquals(MessageStrings.AUTH_TOKEN_NOT_PRESENT, exception.getMessage());

        verify(repository, never()).findTokenByToken(anyString());
        verify(repository, never()).findTokenByClient(any(Client.class));
    }

    @Test
    public void testAuthenticateWithInvalidToken() {
        String token = "invalidToken";

        when(repository.findTokenByToken(token)).thenReturn(null);

        AuthenticationFailException exception = assertThrows(AuthenticationFailException.class, () -> authenticationService.authenticate(token));

        assertEquals(MessageStrings.AUTH_TOKEN_NOT_VALID, exception.getMessage());

        verify(repository, times(1)).findTokenByToken(token);
        verify(repository, never()).findTokenByClient(any(Client.class));
    }
}
