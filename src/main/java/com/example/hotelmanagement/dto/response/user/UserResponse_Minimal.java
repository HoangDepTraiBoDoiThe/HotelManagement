package com.example.hotelmanagement.dto.response.user;

import com.example.hotelmanagement.dto.response.BaseResponse;
import com.example.hotelmanagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse_Minimal extends BaseResponse {
    private String name;

    public UserResponse_Minimal(User user) {
        super(user.getId());
        this.name = user.getName();
    }
}
