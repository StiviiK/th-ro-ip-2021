package de.throsenheim.ip.spm.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Responses used in order to communicate with GitHub's API
 *
 * @author Stefan Kürzeder
 */
public class GitHubApiResponses {
    /**
     * Indicating the response from https://github.com/login/oauth/access_token
     */
    public static class TokenResponse {
        @JsonProperty("access_token")
        public String Token;

        @JsonProperty("scope")
        public String Scope;

        @JsonProperty("token_type")
        public String Type;
    }

    /**
     * Indicating the response from https://api.github.com/user
     */
    public static class UserResponse {
        @JsonProperty("login")
        public String Username;
    }
}
