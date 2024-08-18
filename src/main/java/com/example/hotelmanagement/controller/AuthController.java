package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.Auth.AuthUtils;
import com.example.hotelmanagement.Auth.UserDetail.AuthUserDetail;
import com.example.hotelmanagement.Auth.dtos.LoginRequest;
import com.example.hotelmanagement.Auth.dtos.LoginResponse;
import com.example.hotelmanagement.Auth.dtos.RegisterResponse;
import com.example.hotelmanagement.Auth.dtos.RegisterRequest;
import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.Collection;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthUtils authUtils;
    private final AuthenticationManager authenticationManager;
    
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> userRegister(@RequestBody @Validated RegisterRequest request) throws AuthenticationException {
        userService.createUser(request, null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponse("User created successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody @Validated LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
        AuthUserDetail userDetails = (AuthUserDetail) authentication.getPrincipal();

        String token = authUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token, userDetails.getUsername(), (Collection<GrantedAuthority>) userDetails.getAuthorities()));
    }
}
