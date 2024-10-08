package com.example.hotelmanagement.dto.request;

import com.example.hotelmanagement.model.Utility;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilityRequest {
    @NotBlank(message = "Utility name is required")
    @Size(max = 100, message = "Utility name should be less than or equal to 100 characters")
    String utilityName;
    @NotBlank(message = "Description name is required")
    @Size(max = 2000, message = "Description should be less than or equal to 500 characters")
    String utilityDescription;
    @PositiveOrZero(message = "Price must be positive or zero(Free)")
    BigDecimal baseUtilityPrice;
    boolean status = true;

    public Utility toModel() {
        return new Utility(this.getUtilityName(), this.getBaseUtilityPrice(), this.getUtilityDescription(), this.status);
    }
}
