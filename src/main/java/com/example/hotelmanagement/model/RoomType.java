package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoomType {
    @Id
    @GeneratedValue
    private long Id;

    @NotBlank
    @Size(max = 20, message = "20 characters limited. Room type name should be short and directive, any more information can be put in the description.")
    private String typeName;
    private String description;
    
    @NotBlank
    @Positive
    @Min(1)
    private Number capacity;
    
    @NotBlank
    @PositiveOrZero
    private BigDecimal basePrice;

    @ManyToMany(mappedBy = "roomTypes")
    private List<Room> rooms = new ArrayList<>();
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "room_type_image", joinColumns = @JoinColumn(name = "roomType_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private List<AppPhotos> roomTypeImages;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "Room_Type_Utilities", joinColumns = @JoinColumn(referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private List<Utility> roomTypeUtilities = new ArrayList<>();
}
