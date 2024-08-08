package com.example.hotelmanagement.model;

import com.example.hotelmanagement.constants.ApplicationRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue
    private long id;
    
    @Column(nullable = false)
    @NotBlank
    private ApplicationRole RoleName;
    
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

}
