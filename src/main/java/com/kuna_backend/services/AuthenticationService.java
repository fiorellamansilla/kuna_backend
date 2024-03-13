package com.kuna_backend.services;

import com.kuna_backend.config.MessageStrings;
import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.models.AuthenticationToken;
import com.kuna_backend.models.Client;
import com.kuna_backend.repositories.TokenRepository;
import com.kuna_backend.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final TokenRepository repository;

    @Autowired
    public AuthenticationService(TokenRepository repository) {
        this.repository = repository;
    }

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    public AuthenticationToken getToken(Client client) {
        return repository.findTokenByClient(client);
    }

    public Client getClient (String token) {
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
        if (Helper.notNull(authenticationToken)) {
            if (Helper.notNull(authenticationToken.getClient())) {
                return authenticationToken.getClient();
            }
        }
        return null;
    }
    public void authenticate(String token) throws AuthenticationFailException {
        // Null check
        if (!Helper.notNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        // There is not user with the token
        if (!Helper.notNull(getClient(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }
}
