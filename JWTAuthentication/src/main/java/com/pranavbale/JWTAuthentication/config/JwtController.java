package com.pranavbale.JWTAuthentication.config;

import com.pranavbale.JWTAuthentication.entity.JwtRequest;
import com.pranavbale.JWTAuthentication.entity.JwtResponse;
import com.pranavbale.JWTAuthentication.security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

// Entry point of authenticate the user
@RestController
@RequestMapping("auth")
public class JwtController {

    //    Locates the user based on the username.
//    In the actual implementation,
//    the search may be case-sensitive, or case-insensitive depending on how the implementation instance is configured.
//    In this case, the UserDetails object that comes back may have a username that is of a different case than what was actually requested
    @Autowired
    private UserDetailsService userDetailsService;

    //    Attempts to authenticate the passed Authentication object,
//    returning a fully populated Authentication object (including granted authorities) if successful.
    @Autowired
    private AuthenticationManager manager;

    // Working with the token such as
    // generate the token
    // validate the token
    // check the token expire
    // retrieving any information from token
    @Autowired
    private JwtHelper helper;

    // create a log for the controller class
    private final Logger logger = LoggerFactory.getLogger(JwtController.class);

    // login the user
    // for login user need to enter a username and password
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        // authenticate the username and password is correct or not
        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // authenticate the username and password is correct or not
    private void doAuthenticate(String email, String password) {

        // create a authentication token for the validation
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            // Authentication manager Authenticate the username and password token
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    // throw exception if any have
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
