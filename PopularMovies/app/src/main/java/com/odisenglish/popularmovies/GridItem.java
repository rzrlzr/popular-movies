package com.odisenglish.popularmovies;


import org.json.JSONObject;

public class GridItem {
    JSONObject movieJsonStr;

    public GridItem(JSONObject movieJson) {
        this.movieJsonStr = movieJson;
    }

}