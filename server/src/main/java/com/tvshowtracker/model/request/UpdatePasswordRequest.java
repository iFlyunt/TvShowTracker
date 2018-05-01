package com.tvshowtracker.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
