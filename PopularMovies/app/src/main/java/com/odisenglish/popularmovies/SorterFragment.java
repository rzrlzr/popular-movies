package com.odisenglish.popularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SorterFragment extends Fragment {

    @Bind(R.id.button_popular) Button mPopularButton;
    @Bind(R.id.button_top_rated) Button mTopRatedButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sort_bar_main, container, false);
        ButterKnife.bind(this, rootView);

        mPopularButton.setSelected(true);

        mPopularButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTopRatedButton.setSelected(false);
                mPopularButton.setSelected(true);

                OverviewFragment overviewFragment = new OverviewFragment();
                overviewFragment.updateMovieData("http://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.MOVIE_DB_API_KEY);

            }
        });

        mTopRatedButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTopRatedButton.setSelected(true);
                mPopularButton.setSelected(false);

                OverviewFragment overviewFragment = new OverviewFragment();
                overviewFragment.updateMovieData("http://api.themoviedb.org/3/movie/top_rated?api_key=" + BuildConfig.MOVIE_DB_API_KEY);
            }
        });

        return rootView;
    }



}