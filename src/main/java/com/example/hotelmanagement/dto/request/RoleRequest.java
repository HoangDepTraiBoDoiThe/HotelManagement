package com.example.hotelmanagement.dto.request;

import com.example.hotelmanagement.constants.ApplicationRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    @NotBlank
    private String roleName;
}
