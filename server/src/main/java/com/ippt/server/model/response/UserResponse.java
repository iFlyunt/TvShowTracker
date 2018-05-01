package com.ippt.server.model.response;

import com.ippt.server.entity.Subscriber;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private long id;
    private String username;
    private String firstName;
    private String lastName;

    public UserResponse() {}

    private UserResponse(Subscriber entity) {
        this.setId(entity.getId());
        this.setUsername(entity.getUsername());
        this.setFirstName(entity.getFirstName());
        this.setLastName(entity.getLastName());
    }


    public static UserResponse toResponse(Subscriber entity) {
        return new UserResponse(entity);
    }
}
