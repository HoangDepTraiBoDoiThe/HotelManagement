package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role extends BaseModel {
    @Column(nullable = false)
    @NotBlank
    private String roleName;
    
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
