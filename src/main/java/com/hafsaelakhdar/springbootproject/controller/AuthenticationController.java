package com.hafsaelakhdar.springbootproject.controller;

import com.hafsaelakhdar.springbootproject.exceptions.UserAlreadyExistException;
import com.hafsaelakhdar.springbootproject.request.SignUpRequest;
import com.hafsaelakhdar.springbootproject.request.SigninRequest;
import com.hafsaelakhdar.springbootproject.response.JwtAuthenticationResponse;
import com.hafsaelakhdar.springbootproject.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignUpRequest request) throws UserAlreadyExistException {
        authenticationService.signup(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}