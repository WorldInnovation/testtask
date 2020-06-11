package com.spintech.testtask.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TvShow {
    private String credit_id;
    private String original_name;
    private int id;
    ArrayList<Object> genre_ids = new ArrayList<>();
    private String character;
    @Id
    private String name;
    private String poster_path = null;
    private int vote_count;
    private int vote_average;
    private float popularity;
    private int episode_count;
    private String original_language;
    private String first_air_date;
    private String backdrop_path = null;
    private String overview;
    ArrayList<Object> origin_country = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
