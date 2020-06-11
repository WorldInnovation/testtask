package com.spintech.testtask.service.tmdb;

import com.spintech.testtask.entity.Person;
import com.spintech.testtask.entity.TvShow;

public interface TmdbApi {
    String RESPONSE_PARSE_OPEN_BRACKET = "[";
    String RESPONSE_PARSE_CLOSE_BRACKET = "]";
    Person findPerson(String fullName);
    TvShow findTvShow(String tvShowName);
}
