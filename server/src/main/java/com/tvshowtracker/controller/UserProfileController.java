package com.tvshowtracker.controller;

import com.tvshowtracker.entity.Subscriber;
import com.tvshowtracker.model.response.ErrorResponse;
import com.tvshowtracker.model.response.InfoResponse;
import com.tvshowtracker.model.response.UserResponse;
import com.tvshowtracker.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("")
    public ResponseEntity<?> showUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Subscriber user = userService.findUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse().toResponse(user));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        Subscriber user = userService.findById(id);
        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ErrorResponse("User not found"));
        userService.delete(user);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new InfoResponse("User has been successfully deleted"));
    }
}
