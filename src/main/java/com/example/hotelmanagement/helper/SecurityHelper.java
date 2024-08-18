package com.example.hotelmanagement.helper;

import com.example.hotelmanagement.Auth.UserDetail.AuthUserDetail;
import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.exception.AuthException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SecurityHelper {

    public static boolean checkOwningPermission(Authentication authentication, long userId, boolean throwIfNotMatch) {
        AuthUserDetail principal = extractPrincipal(authentication);
        if (principal == null) return false;
        boolean isOwner = principal.getId() == userId;
        if (!isOwner  && throwIfNotMatch) throw new AuthException("Owner Id does not match the requested id");
        return isOwner;
    }
    
    public static boolean checkAdminPermission(Authentication authentication) {
        Set<String> roles = StaticHelper.extractAllRoles(authentication);
        return roles.contains(ApplicationRole.ADMIN.name());
    }

    public static AuthUserDetail extractPrincipal(Authentication authentication) {
        if (authentication == null) return null;
        return (AuthUserDetail)authentication.getPrincipal();
    }
}
