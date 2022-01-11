package de.throsenheim.ip.spm.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Request used for authentication using username & password.
 *
 * @author Stefan Kürzeder
 */
public class UsernamePasswordAuthRequest {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    public UsernamePasswordAuthRequest() {
    }

    public UsernamePasswordAuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
