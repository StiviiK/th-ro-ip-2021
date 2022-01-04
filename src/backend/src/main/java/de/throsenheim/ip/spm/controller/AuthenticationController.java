package de.throsenheim.ip.spm.controller;

import de.throsenheim.ip.spm.models.AuthenticationRequest;
import de.throsenheim.ip.spm.models.AuthenticationResponse;
import de.throsenheim.ip.spm.service.LocalUserDetailsService;
import de.throsenheim.ip.spm.service.UserPrincipal;
import de.throsenheim.ip.spm.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final LocalUserDetailsService userDetailsService;
    private final JWTUtil jwtTokenUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, LocalUserDetailsService userDetailsService, JWTUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserPrincipal userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final Map<String, Object> claims = new HashMap<>() {{
            put("uid", userDetails.getId());
            put("username", userDetails.getUsername());
        }};

        final String jwt = jwtTokenUtil.generateToken(userDetails, claims);
        return ResponseEntity.ok(new AuthenticationResponse(userDetails.getUsername(), jwt));
    }
}