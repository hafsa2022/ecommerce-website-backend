package com.hafsaelakhdar.springbootproject.service.impl;

import com.hafsaelakhdar.springbootproject.entities.Order;
import com.hafsaelakhdar.springbootproject.entities.OrderStatus;
import com.hafsaelakhdar.springbootproject.entities.Role;
import com.hafsaelakhdar.springbootproject.entities.User;
import com.hafsaelakhdar.springbootproject.exceptions.UserAlreadyExistException;
import com.hafsaelakhdar.springbootproject.repository.OrderRepository;
import com.hafsaelakhdar.springbootproject.repository.UserRepository;
import com.hafsaelakhdar.springbootproject.request.SignUpRequest;
import com.hafsaelakhdar.springbootproject.request.SigninRequest;
import com.hafsaelakhdar.springbootproject.response.JwtAuthenticationResponse;
import com.hafsaelakhdar.springbootproject.service.AuthenticationService;
import com.hafsaelakhdar.springbootproject.service.JwtService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final OrderRepository orderRepository;

    @PostConstruct
    public void createAdminAccount(){

        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if(adminAccount == null){
            User user = new User();
            user.setUsername("admin");
            user.setEmail("admin@gmail.com");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }

    }

    @Override
    public void signup(SignUpRequest request) throws UserAlreadyExistException{

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistException("Email is already in use!");
            //return JwtAuthenticationResponse.builder().message("Email is already in use!").build();
        }
        var user = User.builder().username(request.getUsername())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        User createdUser = userRepository.save(user);
        Order order = new Order();
        order.setPrice(0L);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUser(createdUser);
        orderRepository.save(order);
        //var jwt = jwtService.generateToken(user);
        //return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
    }


    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {

        try{

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        }catch (BadCredentialsException badCredentialsException){

            throw new BadCredentialsException("Invalid email or password.");
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
    }
}