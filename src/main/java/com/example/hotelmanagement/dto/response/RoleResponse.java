package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RoleResponse {

    private String roleName;
    private long id;
    public RoleResponse(String roleName) {
        this.roleName = roleName;
    }

    public RoleResponse(Role role) {
        this.id = role.getId();
        this.roleName = role.getRoleName();
    }

    public static List<RoleResponse> toRoleResponses(List<Role> roles) {
        return roles.stream().map(RoleResponse::new).toList();
    }
}
