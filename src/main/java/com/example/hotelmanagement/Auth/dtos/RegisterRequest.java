package com.example.hotelmanagement.Auth.dtos;

import com.example.hotelmanagement.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(userName, email, passwordEncoder.encode(password), phoneNumber);
    }
}
