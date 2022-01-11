package de.throsenheim.ip.spm.models;

import lombok.Getter;

/**
 * Response containing the Username and a signed JWT Token after authentication.
 *
 * @author Stefan Kürzeder
 */
public class AuthenticationResponse {
    @Getter
    private final String username;

    @Getter
    private final String token;

    public AuthenticationResponse(String username, String jwt) {
        this.username = username;
        this.token = jwt;
    }
}
