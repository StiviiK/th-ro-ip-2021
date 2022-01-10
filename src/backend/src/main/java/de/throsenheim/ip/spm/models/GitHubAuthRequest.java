package de.throsenheim.ip.spm.models;

import lombok.Getter;
import lombok.Setter;

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
