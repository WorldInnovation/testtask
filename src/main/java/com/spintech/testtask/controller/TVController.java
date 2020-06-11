package com.spintech.testtask.controller;

import com.spintech.testtask.entity.Person;
import com.spintech.testtask.entity.TvShow;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.error.PersonNotFoundException;
import com.spintech.testtask.error.TvShowNotFoundException;
import com.spintech.testtask.service.UserService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
             return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(fullName);
        }
        Person person = tmdbApi.findPerson(fullName);

        if (Objects.nonNull(person)){
            userService.addFavoriteActor(user, person);
        }
        userService.addFavoriteActor(user, person);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(person);
    }

    @RequestMapping(value = "/removeActor", method = RequestMethod.POST)
    public ResponseEntity removeFavoriteActor(@RequestParam String email,
                                              @RequestParam String password,
                                              @RequestParam String name,
                                              @RequestParam String lastName){

        String fullName = name.concat(NAME_SEPARATOR).concat(lastName);
        User user = userService.findUser(email, password);
        if (fullName.equals(NAME_SEPARATOR)|| Objects.isNull(user)) {
            throw new PersonNotFoundException(fullName);
        }
        userService.removeFavoriteActor(user, fullName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(fullName);
    }

    @RequestMapping(value = "/addWatchedTv", method = RequestMethod.POST)
    public ResponseEntity addWatchedTv(@RequestParam String email,
                                       @RequestParam String password,
                                       @RequestParam String tvShowName) {

        User user = userService.findUser(email, password);
        if (Objects.isNull(user) || Strings.isBlank(tvShowName)) {
            throw new TvShowNotFoundException(tvShowName);
        }
        TvShow tvShow = tmdbApi.findTvShow(tvShowName);

        if (Objects.nonNull(tvShow)){
            userService.addTvShowWatched(user, tvShow);
        }

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(tvShow);
    }

    @RequestMapping(value = "/unSelectWatchedTv", method = RequestMethod.POST)
    public ResponseEntity unSelectWatchedTv(@RequestParam String email,
                                            @RequestParam String password,
                                            @RequestParam String tvShowName) {

        User user = userService.findUser(email, password);
        if ( Objects.isNull(user) || Strings.isBlank(tvShowName)) {
            throw new TvShowNotFoundException(tvShowName);
        }
        TvShow tvShow = tmdbApi.findTvShow(tvShowName);
        if (Objects.nonNull(tvShow)){
            userService.unSelectTvShowWatched(user, tvShow);
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(tvShowName);
    }

    @RequestMapping(value = "/tvFavoriteActorUnwatched", method = RequestMethod.GET)
    public List<TvShow> tvFavoriteActorUnwatched(@RequestParam(required = false, defaultValue = "test@test2.com") String email,
                                                 @RequestParam(required = false, defaultValue = "test") String password) {

        List<TvShow> tvShows = new ArrayList<>();
        User user = userService.findUser(email, password);
        if ( Objects.isNull(user) || user.getFavoriteActors().isEmpty() ) {
            return tvShows;
        }
        return tmdbApi.finedTvFavoriteActorUnwatched(user);
    }
}
