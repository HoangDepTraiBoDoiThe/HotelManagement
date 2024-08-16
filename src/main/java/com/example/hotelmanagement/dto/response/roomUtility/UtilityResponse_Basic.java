package com.example.hotelmanagement.dto.response.roomUtility;

import com.example.hotelmanagement.model.Utility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UtilityResponse_Basic extends UtilityResponse_Minimal {
    private BigDecimal utilityBasePrice;
    private String utilityDescription;
    public UtilityResponse_Basic(Utility utility) {
        super(utility);
        this.utilityBasePrice = utility.getUtilityBasePrice();
        this.utilityDescription = utility.getUtilityDescription();
    }
}
