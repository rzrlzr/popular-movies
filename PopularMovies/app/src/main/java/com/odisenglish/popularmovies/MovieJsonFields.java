package com.odisenglish.popularmovies;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieJsonFields {

    String mMoviePosterUrl;
    String mMovieDescription;
    String mMovieTitle;
    String mMovieReleaseDate;
    String mMovieVoteAverage;

    public MovieJsonFields(String moviesJsonStr) throws JSONException {

        JSONObject moviesUrlJson = new JSONObject(moviesJsonStr);
        String movieUrl = moviesUrlJson.getString("poster_path");
        String movieDescription = moviesUrlJson.getString("overview");
        String movieTitle = moviesUrlJson.getString("title");
        String movieReleaseDate = moviesUrlJson.getString("release_date");
        String movieVoteAverage = moviesUrlJson.getString("vote_average");

        mMoviePosterUrl = movieUrl;
        mMovieDescription = movieDescription;
        mMovieTitle = movieTitle;
        mMovieReleaseDate = movieReleaseDate;
        mMovieVoteAverage = movieVoteAverage;

    }



}
