package com.example.hotelmanagement.dto.response.roomUtility;

import com.example.hotelmanagement.dto.response.ResponseBase;
import com.example.hotelmanagement.model.Utility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UtilityResponse_Minimal extends ResponseBase {
    private String utilityName;

    public UtilityResponse_Minimal(Utility utility) {
        super(utility.getId());
        this.utilityName = utility.getUtilityName();
    }
}
