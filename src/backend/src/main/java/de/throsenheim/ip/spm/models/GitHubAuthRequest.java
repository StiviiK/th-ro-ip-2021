package de.throsenheim.ip.spm.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Request used for authentication using the GitHub auth code.
 *
 * @author Stefan Kürzeder
 */
public class GitHubAuthRequest {
    @Getter
    @Setter
    private String code;

    public GitHubAuthRequest() {
    }

    public GitHubAuthRequest(String code) {
        this.code = code;
    }
}
