package com.example.hotelmanagement.dto.response.user;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.dto.response.ResponseBase;
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
public class UserResponse_Minimal extends ResponseBase {
    private String name;

    public UserResponse_Minimal(User user) {
        super(user.getId());
        this.name = user.getName();
    }
}
