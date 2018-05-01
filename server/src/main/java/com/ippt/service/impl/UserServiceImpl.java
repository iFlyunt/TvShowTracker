package com.ippt.service.impl;

import com.ippt.entity.Subscriber;
import com.ippt.model.request.RegistrationRequest;
import com.ippt.repository.UserRepository;
import com.ippt.service.AbstractService;
import com.ippt.service.UserService;
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
