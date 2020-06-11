package com.spintech.testtask.controller;

import com.spintech.testtask.entity.Person;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.error.PersonNotFoundException;
import com.spintech.testtask.service.UserService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/tv")
public class TVController {
    private static final String NAME_SEPARATOR = " ";

    @Autowired
    private UserService userService;

    @Autowired
    private TmdbApi tmdbApi;

    @RequestMapping(value = "/addActor", method = RequestMethod.POST)
    public ResponseEntity addFavoriteActor(@RequestParam String email,
                                           @RequestParam String password,
                                           @RequestParam String name,
                                           @RequestParam String lastName) {

        String fullName = name.concat(NAME_SEPARATOR).concat(lastName);

        User user = userService.findUser(email, password);
        if (fullName.equals(NAME_SEPARATOR) || Objects.isNull(user)) {
            throw new PersonNotFoundException(fullName);
        }
        Person person = tmdbApi.findPerson(fullName);

        if (Objects.nonNull(person)){
            userService.addFavoriteActor(user, person);
        }

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(person);
    }

}
