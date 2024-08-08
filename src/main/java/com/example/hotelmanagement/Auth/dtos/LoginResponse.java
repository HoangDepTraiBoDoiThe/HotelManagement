package com.example.hotelmanagement.Auth.dtos;

import com.example.hotelmanagement.constants.ApplicationRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String userName;
    private Collection<GrantedAuthority> roleList;
}
