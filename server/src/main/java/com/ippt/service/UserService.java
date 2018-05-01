package com.ippt.service;

import com.ippt.entity.Subscriber;
import com.ippt.model.request.RegistrationRequest;
import com.ippt.repository.UserRepository;

public interface UserService extends Service<Subscriber, Long, UserRepository> {
    void addNewUser(RegistrationRequest request);

    Subscriber findUserByUsername(String username);
}
