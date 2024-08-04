package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Users")
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private long phoneNumber;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "UserRole", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "Id"))
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "owner")
    private List<Reservation> reservations = new ArrayList<>();
}
