package com.ippt.server.service;

import com.ippt.server.entity.Subscriber;
import com.ippt.server.model.request.RegistrationRequest;
import com.ippt.server.model.response.UserResponse;
import com.ippt.server.repository.SubscriptionRepository;
import com.ippt.server.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserService extends Service<Subscriber, Long, UserRepository> {
    void addNewUser(RegistrationRequest request);

    Subscriber findUserByUsername(String username);
}
