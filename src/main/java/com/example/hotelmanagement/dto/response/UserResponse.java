package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.model.Role;
import com.example.hotelmanagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends ResponseBase{
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private List<String> roles = new ArrayList<>();

    public UserResponse(User user) {
        super(user.getId());
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        if (!user.getRoles().isEmpty()) {
            this.roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        } else {
            this.roles.add(ApplicationRole.NONE.name());
        } 
        
    }
}
