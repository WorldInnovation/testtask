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
public class ActorWithTvShows {
    ArrayList<TvShow> cast = new ArrayList<>();
    @Id
    ArrayList<Object> crew = new ArrayList<>();
    private int id;


    public ArrayList<TvShow> getCast() {
        return cast;
    }

    public void setCast(ArrayList<TvShow> cast) {
        this.cast = cast;
    }

    public ArrayList<Object> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Object> crew) {
        this.crew = crew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
