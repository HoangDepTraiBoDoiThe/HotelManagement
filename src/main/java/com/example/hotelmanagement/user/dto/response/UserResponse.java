package com.example.hotelmanagement.user.dto.response;

import com.example.hotelmanagement.role.model.Role;
import com.example.hotelmanagement.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private String password;
    private long phoneNumber;
    private List<String> roles;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
    }
}
