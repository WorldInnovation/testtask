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
public class Person {

    private double popularity;

    private String known_for_department;
    @Id
    private String name;

    private int id;

    private String profile_path;

    private boolean adult;

    ArrayList<Object> known_for = new ArrayList<Object>();

    private int gender;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
