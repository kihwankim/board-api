package com.cnu.spg.security.service;

import com.cnu.spg.user.domain.User;
import com.cnu.spg.user.domain.UserPrincipal;
import com.cnu.spg.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = this.userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("I can not found user data" + userName));

        return UserPrincipal.create(user);
    }
}
