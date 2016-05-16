package com.odisenglish.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import java.util.List;


public class GridItemAdapter extends ArrayAdapter<GridItem> {

    public GridItemAdapter(Activity context, List<GridItem> gridItems) {

        super(context, 0, gridItems);

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        GridItem gridItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_main, parent, false);
        }

        try {
            MovieJsonFields movieItems = new MovieJsonFields(gridItem.movieJsonStr.toString());
            String moviePosterUrl = movieItems.mMoviePosterUrl;

            ImageView moviePoster = (ImageView) convertView.findViewById(R.id.grid_item_image);
            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185//" + moviePosterUrl).into(moviePoster);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

}
