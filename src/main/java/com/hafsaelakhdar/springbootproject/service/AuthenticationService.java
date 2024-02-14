package com.hafsaelakhdar.springbootproject.service;


import com.hafsaelakhdar.springbootproject.exceptions.UserAlreadyExistException;
import com.hafsaelakhdar.springbootproject.request.SignUpRequest;
import com.hafsaelakhdar.springbootproject.request.SigninRequest;
import com.hafsaelakhdar.springbootproject.response.JwtAuthenticationResponse;


public interface AuthenticationService {

    void createAdminAccount();

   void signup(SignUpRequest request) throws UserAlreadyExistException;

    JwtAuthenticationResponse signin(SigninRequest request);
}