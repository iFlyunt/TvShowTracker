package com.tvshowtracker.service.impl;

import com.tvshowtracker.entity.Subscriber;
import com.tvshowtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service("customUserDetailService")
public class UserSecurityServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Subscriber subscriber = userRepository.findUserByUsername(username);
        return Optional.ofNullable(subscriber).map(s -> new User(s.getUsername(), s.getPassword(),
                                                                 new ArrayList<>()))
                       .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
