package com.example.hotelmanagement.user.model;

import com.example.hotelmanagement.reservation.model.Reservation;
import com.example.hotelmanagement.role.model.Role;
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
    private String email;
    private String password;
    private long phoneNumber;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "UserRole", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "Id"))
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "owner")
    private List<Reservation> reservations = new ArrayList<>();
}
