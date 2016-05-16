package com.odisenglish.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;
import org.json.JSONException;


public class MovieDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String movieDescription;

        Intent intent = getIntent();
        String movieJsonObject = intent.getExtras().getString("movieJsonObject");

        try {
            MovieJsonFields movieItems = new MovieJsonFields(movieJsonObject);

            movieDescription = movieItems.mMovieDescription;
            TextView movieDescriptionTextView = (TextView) findViewById(R.id.descriptionTextView);

            movieDescriptionTextView.setText(movieDescription);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }



}
