package com.example.hotelmanagement.dto.request;

import com.example.hotelmanagement.constants.ApplicationRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private ApplicationRole roleName;
}
