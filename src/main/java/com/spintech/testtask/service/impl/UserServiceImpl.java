package com.spintech.testtask.service.impl;

import com.spintech.testtask.entity.Person;
import com.spintech.testtask.entity.TvShow;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.repository.UserRepository;
import com.spintech.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (Objects.nonNull(user)) {
            return null;
        }
        user = User.builder().email(email).password(passwordEncoder.encode(password)).build();
        return userRepository.save(user);
    }

    @Override
    public User findUser(String email, String password) {
        User foundUser = userRepository.findByEmail(email);
        if (Objects.nonNull(foundUser)) {
            if (passwordEncoder.matches(password, foundUser.getPassword())) {
                return foundUser;
            }
        }
        return null;
    }

    @Override
    public User addFavoriteActor(User user,Person person) {
        HashMap<String, Integer> favoriteActorsMap = (user.getFavoriteActors() == null)
                ? new HashMap<>()
                : new HashMap<>(user.getFavoriteActors());
        favoriteActorsMap.put(person.getName(),person.getId());
        user.setFavoriteActors(favoriteActorsMap);
        userRepository.save(user);
        return user;
    }

    @Override
    public User removeFavoriteActor(User user,String fullName) {
        HashMap<String, Integer> favoriteActorsMap = (user.getFavoriteActors() == null)
                ? new HashMap<>()
                : new HashMap<>(user.getFavoriteActors());
        if (favoriteActorsMap.containsKey(fullName)){
            favoriteActorsMap.remove(fullName);
            user.setFavoriteActors(favoriteActorsMap);
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public User addTvShowWatched(User user, TvShow tvShow) {
        HashMap<String, Integer> watchedTvShows = (user.getWatchedTvShows() == null)
                ? new HashMap<>()
                : new HashMap<>(user.getWatchedTvShows());
        watchedTvShows.put(tvShow.getName(), tvShow.getId());
        user.setWatchedTvShows(watchedTvShows);
        userRepository.save(user);
        return user;
    }

    @Override
    public User unSelectTvShowWatched(User user, TvShow tvShow) {
        HashMap<String, Integer> watchedTvShows = (user.getWatchedTvShows() == null)
                ? new HashMap<>()
                : new HashMap<>(user.getWatchedTvShows());
        if (watchedTvShows.containsKey(tvShow.getName())) {
            watchedTvShows.remove(tvShow.getName());
            user.setWatchedTvShows(watchedTvShows);
            userRepository.save(user);
        }
        return user;
    }
}