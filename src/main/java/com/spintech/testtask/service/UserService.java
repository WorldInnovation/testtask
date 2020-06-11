package com.spintech.testtask.service;

import com.spintech.testtask.entity.Person;
import com.spintech.testtask.entity.TvShow;
import com.spintech.testtask.entity.User;

public interface UserService {
    User registerUser(String email, String password);
    User findUser(String email, String password);
    User addFavoriteActor(User user, Person person);
    User removeFavoriteActor(User user, String fullName);
    User addTvShowWatched(User user, TvShow tvShow);
}

