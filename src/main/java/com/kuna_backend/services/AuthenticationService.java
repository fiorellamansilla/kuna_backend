package com.kuna_backend.services;

import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.models.AuthenticationToken;
import com.kuna_backend.models.Client;
import com.kuna_backend.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {
    @Autowired
    TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(Client client) {
        return tokenRepository.findTokenByClient(client);
    }

    public Client getClient (String token) {
        final AuthenticationToken authenticationToken = tokenRepository.findTokenByToken(token);
        // The authentication token is null
        if (Objects.isNull(authenticationToken)) {
            return null;
        }
        // The authentication token is not null
        return authenticationToken.getClient();
    }
    public void authenticate(String token) throws AuthenticationFailException {
        // Null check
        if (Objects.isNull(token)) {
            throw new AuthenticationFailException("The token doesn't exist");
        }
        // There is not user with the token
        if (Objects.isNull(getClient(token))) {
            throw new AuthenticationFailException("The token is not valid");
        }
    }
}
