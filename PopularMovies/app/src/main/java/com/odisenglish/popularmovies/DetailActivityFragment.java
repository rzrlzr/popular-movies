package com.odisenglish.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import org.json.JSONException;


public class DetailActivityFragment extends Fragment {

    @Bind(R.id.detail_activity_image) ImageView moviePoster;
    @Bind(R.id.movie_title_text_view) TextView movieTitleTextView;
    @Bind(R.id.movie_release_date_text_view) TextView movieReleaseDateTextView;
    @Bind(R.id.movie_vote_average_text_view) TextView movieVoterAverageTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_activity_image_fragment, container, false);
        ButterKnife.bind(this, rootView);

        String movieJsonObject = getActivity().getIntent().getExtras().getString("movieJsonObject");

        try {
            MovieJsonFields movieItems = new MovieJsonFields(movieJsonObject);

            String posterUrl = movieItems.mMoviePosterUrl;
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185//" + posterUrl).into(moviePoster);

            String movieTitle = movieItems.mMovieTitle;
            movieTitleTextView.setText(movieTitle);

            String movieReleaseDate = movieItems.mMovieReleaseDate;
            movieReleaseDateTextView.setText("Release Date: " + movieReleaseDate);

            String movieVoterAverage = movieItems.mMovieVoteAverage;
            movieVoterAverageTextView.setText("Voter Average: " + movieVoterAverage);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return rootView;

    }
}
