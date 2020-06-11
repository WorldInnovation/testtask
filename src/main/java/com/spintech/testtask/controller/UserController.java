package com.spintech.testtask.controller;

import com.spintech.testtask.entity.User;
import com.spintech.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.security.Principal;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = POST)
    public ResponseEntity registerUser(@RequestParam String email,
                                               @RequestParam String password) {
        if (userService.registerUser(email, password) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping( method = RequestMethod.GET)
    public User getTestUser(@RequestParam(required = false, defaultValue = "test@test2.com") String email,
                            @RequestParam(required = false, defaultValue = "test") String password,
                            HttpServletRequest request ){
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            principal.getName();
        }

        User user = userService.findUser(email, password);
        return Objects.nonNull(user) ? user : new User();
    }
}
