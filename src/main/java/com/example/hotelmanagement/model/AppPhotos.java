package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppPhotos extends BaseModel{
    @Lob
    @Column(nullable = false)
    private Blob image;
    private String description;
    private String name;
    
    @ManyToMany(mappedBy = "roomImage")
    private Set<Room> rooms = new HashSet<>();    
}
