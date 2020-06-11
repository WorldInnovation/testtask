package com.spintech.testtask.service.tmdb.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spintech.testtask.entity.ActorWithTvShows;
import com.spintech.testtask.entity.Person;
import com.spintech.testtask.entity.TvShow;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.service.tmdb.TmdbApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TmdbApiImpl implements TmdbApi {
    private static final Logger log = LoggerFactory.getLogger(TmdbApiImpl.class);

    @Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;
    @Value("${tmdb.search}")
    private String tmdbSearch;
    @Value("${tmdb.person}")
    private String tmdbPerson;
    @Value("${tmdb.tv}")
    private String tmdbTv;

    @Override
    public Person findPerson(String fullName) {
        try {
            String url = getSearchUrl(tmdbPerson, fullName);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return null;
            }
            String parse = cutResultsFromResponce(response.getBody());
            return new ObjectMapper().readValue(parse, Person.class);

        } catch (URISyntaxException e) {
            log.error("Couldn't get uri for Person", e);
        } catch (JsonProcessingException e) {
            log.error("Couldn't get json Person", e);
        }
        return null;
    }

    private String getSearchUrl(String searchParam, String query) throws URISyntaxException {
        StringBuilder builder = new StringBuilder(tmdbApiBaseUrl);
        builder.append(tmdbSearch);
        builder.append(searchParam);
        URIBuilder uriBuilder = getUriBuilder(builder.toString());
        uriBuilder.addParameter("query", query);
        return uriBuilder.build().toString();
    }

    @Override
    public TvShow findTvShow(String tvShowName)  {
        try {
            String url = getSearchUrl(tmdbTv, tvShowName);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return null;
            }

            String parse = cutResultsFromResponce(response.getBody());
            return new ObjectMapper().readValue(parse, TvShow.class);
        } catch (URISyntaxException e) {
            log.error("Couldn't get uri for TvShow", e);
        } catch (JsonProcessingException e) {
            log.error("Couldn't get json TvShow", e);
        }

        return null;
    }

    @Override
    public List<TvShow> finedTvFavoriteActorUnwatched(User user) {
        Set<TvShow> tvShows = new HashSet<>();
        HashMap<String, Integer> favoriteActorsMap = new HashMap<>(user.getFavoriteActors());
        for (HashMap.Entry<String, Integer> favoritActor  : favoriteActorsMap.entrySet()
                ) {
            try {
                String personSelectId = tmdbPerson + "/" + favoritActor.getValue() + "/tv_credits";
                String url = getTmdbUrl(personSelectId);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response
                        = restTemplate.getForEntity(url, String.class);

                if (!response.getStatusCode().is2xxSuccessful()) {
                    return null;
                }
                String parse = response.getBody();
                ActorWithTvShows actorWithTvShows = new ObjectMapper().readValue(parse, ActorWithTvShows.class);
                tvShows.addAll(actorWithTvShows.getCast());//List<Object> currentActor = new ArrayList<>(actorWithTvShows.getCast());
            } catch (URISyntaxException e) {
                log.error("Couldn't get uri TvShow", e);
            }  catch (JsonProcessingException e) {
                log.error("Couldn't get json TvShow", e);
            }

        }
        ArrayList<TvShow> tvShowsCutWatchedList = new ArrayList<>(tvShows);
        if (user.getWatchedTvShows() != null){
            HashMap<String, Integer> watchedTvShowMap = user.getWatchedTvShows();
            List<TvShow> removeList = tvShows.stream().filter(tvShowFilter -> watchedTvShowMap.containsKey(tvShowFilter.getName()))
                    .collect(Collectors.toList());
            tvShowsCutWatchedList.removeAll(removeList);
        }

        return tvShowsCutWatchedList;
    }

    private String getTmdbUrl(String tmdbItem) throws URISyntaxException {
        StringBuilder builder = new StringBuilder(tmdbApiBaseUrl);
        builder.append(tmdbItem);
        URIBuilder uriBuilder = getUriBuilder(builder.toString());
        return uriBuilder.build().toString();
    }

    private URIBuilder getUriBuilder (String builder) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(builder);
        uriBuilder.addParameter("language", tmdbLanguage);
        uriBuilder.addParameter("api_key", tmdbApiKey);
        return uriBuilder;
    }

    private String cutResultsFromResponce(String response){
        StringBuilder sb = new StringBuilder(response);
        Integer firstSymbol = sb.indexOf(RESPONSE_PARSE_OPEN_BRACKET) + 1;
        Integer lastSymbol = sb.lastIndexOf(RESPONSE_PARSE_CLOSE_BRACKET);
        return sb.substring(firstSymbol , lastSymbol );
    }

}
