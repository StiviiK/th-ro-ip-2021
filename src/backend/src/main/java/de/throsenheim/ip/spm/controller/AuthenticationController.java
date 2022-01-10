package de.throsenheim.ip.spm.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.throsenheim.ip.spm.models.GitHubAuthRequest;
import de.throsenheim.ip.spm.models.User;
import de.throsenheim.ip.spm.models.UsernamePasswordAuthRequest;
import de.throsenheim.ip.spm.models.AuthenticationResponse;
import de.throsenheim.ip.spm.repository.UserRepository;
import de.throsenheim.ip.spm.service.LocalUserDetailsService;
import de.throsenheim.ip.spm.service.UserPrincipal;
import de.throsenheim.ip.spm.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AuthenticationController {

    private static class TokenResponse {
        @JsonProperty("access_token")
        String Token;

        @JsonProperty("scope")
        String Scope;

        @JsonProperty("token_type")
        String Type;
    }

    private static class UserResponse {
        @JsonProperty("login")
        String Username;
    }

    @Value("${github.client_id}")
    private String ClientId;
    @Value("${github.client_secret}")
    private String ClientSecret;

    private final AuthenticationManager authenticationManager;
    private final LocalUserDetailsService userDetailsService;
    private final JWTUtil jwtTokenUtil;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, LocalUserDetailsService userDetailsService,
                                    JWTUtil jwtTokenUtil, RestTemplateBuilder restTemplateBuilder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.restTemplate = restTemplateBuilder.build();
        this.userRepository = userRepository;
    }

    private String signJWTFor(String username, Boolean createIfNotExists) throws UsernameNotFoundException {
        UserPrincipal userDetails;

        // Load the user if it exists
        try {
            userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            if (createIfNotExists) { // Only create the user in special cases, like successful GitHub authentication
                var user = new User(username, BCrypt.hashpw(UUID.randomUUID().toString(), BCrypt.gensalt())); // Assign a random password
                userRepository.save(user);

                userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(username);
            } else { // Rethrow the Exception in "normal" authentication cases
                throw ex;
            }
        }

        UserPrincipal finalUserDetails = userDetails;
        final Map<String, Object> claims = new HashMap<>() {{
            put("uid", finalUserDetails.getId());
            put("username", finalUserDetails.getUsername());
        }};

        return jwtTokenUtil.generateToken(userDetails, claims);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<?> authenticate(@RequestBody UsernamePasswordAuthRequest authenticationRequest) throws Exception {
        try {
            // validate username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final String jwt = signJWTFor(authenticationRequest.getUsername(), false);
        return ResponseEntity.ok(new AuthenticationResponse(authenticationRequest.getUsername(), jwt));
    }

    @RequestMapping(value = "/authenticate/github", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<?> authenticateWithGitHub(@RequestBody GitHubAuthRequest authenticationRequest) throws Exception {
        var values = new HashMap<String, String>() {{
            put("client_id", ClientId);
            put("client_secret", ClientSecret);
            put("code", authenticationRequest.getCode());
        }};

        // validate and request the final auth token
        final TokenResponse response = this.restTemplate.postForObject("https://github.com/login/oauth/access_token", values, TokenResponse.class);
        if (response == null) {
            throw new Exception("Invalid response from GitHub");
        }

        // read the user data
        var headers = new HttpHeaders() {{
            set("Authorization", "token " + response.Token);
        }};
        final ResponseEntity<UserResponse> userEntity = this.restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, new HttpEntity<String>(headers), UserResponse.class);
        if (userEntity.getBody() == null) {
            throw new Exception("Invalid authentication");
        }

        final String jwt = signJWTFor(userEntity.getBody().Username, true);
        return ResponseEntity.ok(new AuthenticationResponse(userEntity.getBody().Username, jwt));
    }
}
