package com.tvshowtracker.service.impl;

import com.tvshowtracker.entity.Subscriber;
import com.tvshowtracker.model.request.RegistrationRequest;
import com.tvshowtracker.repository.UserRepository;
import com.tvshowtracker.service.AbstractService;
import com.tvshowtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService<Subscriber, Long, UserRepository>
        implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(@Autowired UserRepository repository) {
        super(repository);
    }

    @Override
    public void addNewUser(RegistrationRequest request) {
        Subscriber subscriber = new Subscriber();
        subscriber.setUsername(request.getUsername());
        subscriber.setPassword(passwordEncoder.encode(request.getPassword()));
        subscriber.setFirstName(request.getFirstName());
        subscriber.setLastName(request.getLastName());
        super.save(subscriber);
    }

    @Override
    public Subscriber findUserByUsername(String username) {
        return getRepository().findUserByUsername(username);
    }
}
