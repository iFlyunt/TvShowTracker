package com.tvshowtracker.service;

import com.tvshowtracker.entity.Subscriber;
import com.tvshowtracker.model.request.RegistrationRequest;
import com.tvshowtracker.repository.UserRepository;

public interface UserService extends Service<Subscriber, Long, UserRepository> {
    void addNewUser(RegistrationRequest request);

    Subscriber findUserByUsername(String username);
}
