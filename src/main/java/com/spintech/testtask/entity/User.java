package com.spintech.testtask.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String email;

    private String password;

    HashMap<String, Integer> favoriteActors;

    HashMap<String, Integer> watchedTvShows;

    public String getPassword() {
        return password;
    }

    public HashMap<String, Integer> getFavoriteActors() {
        return favoriteActors;
    }

    public void setFavoriteActors(HashMap<String, Integer> favoriteActors) {
        this.favoriteActors = favoriteActors;
    }

    public HashMap<String, Integer> getWatchedTvShows() {
        return watchedTvShows;
    }

    public void setWatchedTvShows(HashMap<String, Integer> watchedTvShows) {
        this.watchedTvShows = watchedTvShows;
    }
}