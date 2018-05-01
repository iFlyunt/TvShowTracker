package com.ippt.server.repository;

import com.ippt.server.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Subscriber, Long> {
    @Query("select u from Subscriber u where u.username = :username")
    Subscriber findUserByUsername(@Param("username") String username);
}
