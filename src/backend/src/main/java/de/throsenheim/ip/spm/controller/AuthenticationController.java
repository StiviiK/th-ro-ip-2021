package de.throsenheim.ip.spm.controller;

import de.throsenheim.ip.spm.models.*;
import de.throsenheim.ip.spm.repository.UserRepository;
import de.throsenheim.ip.spm.service.LocalUserDetailsService;
import de.throsenheim.ip.spm.models.UserPrincipal;
import de.throsenheim.ip.spm.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controller that handles all types of authentication.
 * Including Username & Password and GitHub
 *
 * @author Stefan Kürzeder
 */
@RestController
public class AuthenticationController {

    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;

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

    /**
     * Looks up if the given user exists and signs an JWT for him.
     * @param username The username to sign the JWT for.
     * @param createIfNotExists Create the User if it doesn't exist yet. Used for GitHub authentication only.
     * @return The signed JWT as a String.
     * @throws UsernameNotFoundException Will be thrown if the given user does not exist.
     */
    private String signJWTFor(String username, Boolean createIfNotExists) throws UsernameNotFoundException {
        UserPrincipal userDetails;

        // Load the user if it exists
        try {
            userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            if (Boolean.TRUE.equals(createIfNotExists)) { // Only create the user in special cases, like successful GitHub authentication
                var user = new User(username, BCrypt.hashpw(UUID.randomUUID().toString(), BCrypt.gensalt())); // Assign a random password
                userRepository.save(user);

                userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(username);
            } else { // Rethrow the Exception in "normal" authentication cases
                throw ex;
            }
        }

        UserPrincipal finalUserDetails = userDetails;
        final Map<String, Object> claims = new HashMap<>();
        claims.put("uid", finalUserDetails.getId());
        claims.put("username", finalUserDetails.getUsername());

        return jwtTokenUtil.generateToken(userDetails, claims);
    }

    /**
     * Handles the Username & Password authentication.
     * @param authenticationRequest The Username & Password as the body.
     * @return A successful response containing the signed JWT and the Username.
     * @throws BadCredentialsException If the provided credentials are invalid.
     * @throws UsernameNotFoundException If the provided User cannot be found.
     */
    @PostMapping(value = "/authenticate", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UsernamePasswordAuthRequest authenticationRequest) throws BadCredentialsException, UsernameNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        final String jwt = signJWTFor(authenticationRequest.getUsername(), false);
        return ResponseEntity.ok(new AuthenticationResponse(authenticationRequest.getUsername(), jwt));
    }

    /**
     * Handles the GitHub authentication using the provided auth code.
     * @param authenticationRequest The auth code as the body.
     * @return A successful response containing the signed JWT and the Username.
     * @throws BadCredentialsException If the provided auth code was invalid or GitHub rejected our request.
     */
    @PostMapping(value = "/authenticate/github", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<AuthenticationResponse> authenticateWithGitHub(@RequestBody GitHubAuthRequest authenticationRequest) throws BadCredentialsException {
        // prepare the access_token request
        final Map<String, String> values = new HashMap<>();
        values.put("client_id", clientId);
        values.put("client_secret", clientSecret);
        values.put("code", authenticationRequest.getCode());

        // validate and request the final auth token
        final GitHubApiResponses.TokenResponse response = this.restTemplate.postForObject("https://github.com/login/oauth/access_token", values, GitHubApiResponses.TokenResponse.class);
        if (response == null) {
            throw new BadCredentialsException("Invalid response from GitHub");
        }

        // read the user data
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + response.token);

        final ResponseEntity<GitHubApiResponses.UserResponse> userEntity = this.restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, new HttpEntity<String>(headers), GitHubApiResponses.UserResponse.class);
        final GitHubApiResponses.UserResponse userResponse = userEntity.getBody();
        if (userResponse == null) {
            throw new BadCredentialsException("Invalid authentication");
        }

        // user authentication has been successfully validated, sign the jwt
        final String jwt = signJWTFor(userResponse.username, true);
        return ResponseEntity.ok(new AuthenticationResponse(userResponse.username, jwt));
    }
}
