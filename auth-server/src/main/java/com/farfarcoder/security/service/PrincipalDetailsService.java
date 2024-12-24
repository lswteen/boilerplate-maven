package com.farfarcoder.security.service;

import com.farfarcoder.domain.user.model.User;
import com.farfarcoder.domain.user.service.UserService;
import com.farfarcoder.security.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userService.findByUserId(userId);
        if (user == null) {
            return null;
        }else{
            return new PrincipalDetails(user);
        }

    }
}
