package com.example.hotelmanagement.Auth.UserDetail;

import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username).orElseThrow(() -> new  UsernameNotFoundException(String.format(username, "Can not find any user with this user name: [%s]")));
        AuthUserDetail userDetail = new AuthUserDetail(user.getName(), user.getPassword(), user.getEmail());
        userDetail.setOwningAuthorities(user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList()));
        
        return userDetail;
    }
}
