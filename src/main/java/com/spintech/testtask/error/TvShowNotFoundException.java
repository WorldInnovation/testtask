package com.spintech.testtask.error;

public class TvShowNotFoundException extends  RuntimeException{

    public TvShowNotFoundException(String tvShowName) {
        super("Tv sow name not found :" + tvShowName);
    }
}
