package com.example.hotelmanagement;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.helper.StaticHelper;
import com.example.hotelmanagement.model.Role;
import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.model.repository.RoleRepository;
import com.example.hotelmanagement.model.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    // logger
    private static final Logger logger = Logger.getLogger(DataInitializer.class.getName());
    
    // fields
    private final StaticHelper staticHelper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${auth.security.initData.adminPassword}")
    private String initPassword;
    @Value("${auth.security.initData.adminPhoneNumber}")
    private String initPhoneNumber;
    
    @PostConstruct
    public void initData() {
        if (roleRepository.findAll().isEmpty()) {
            List<ApplicationRole> applicationRoles = new ArrayList<>();
            applicationRoles.add(ApplicationRole.GUESS);
            applicationRoles.add(ApplicationRole.ADMIN);
            applicationRoles.add(ApplicationRole.MANAGER);

            applicationRoles.stream().map(role -> roleRepository.save(new Role(role.name()))).forEach(role -> {});
            logger.info("Initialize initial roles");
        }
        if (userRepository.findUsersByRoleName(ApplicationRole.ADMIN.name()).isEmpty()) {
            User newUserAsAdmin = new User("Admin", "DummyEmail@Gmail.com", passwordEncoder.encode(initPassword), initPhoneNumber);
            Role adminRole = roleRepository.findRoleByRoleName(ApplicationRole.ADMIN.name()).orElseGet(() -> roleRepository.save(new Role(ApplicationRole.ADMIN.name())));
            newUserAsAdmin.getRoles().add(adminRole);
            userRepository.save(newUserAsAdmin);
            logger.info("Initialize initial admin");
        }
    }
}
