package com.odisenglish.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class OverviewFragment extends Fragment {

    private static GridItemAdapter gridAdapter;
    private String address;
    private static GridItem[] moviesId = new GridItem[20];
    @Bind(R.id.gridview_movies) GridView gridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        address = "http://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.MOVIE_DB_API_KEY;

        List<GridItem> moviesId = new ArrayList<GridItem>();
        gridAdapter = new GridItemAdapter(getActivity(), moviesId);


        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                GridItem gridItem = gridAdapter.getItem(position);
                JSONObject movieJson = gridItem.movieJsonStr;

                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("movieJsonObject", movieJson.toString());

                try {
                    String posterUrl = movieJson.getString("poster_path");
                    intent.putExtra("posterUrl", posterUrl);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(intent);

            }
        });


        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        updateMovieData(address);
    }

    public void updateMovieData(String address) {
        FetchMovieData movieData = new FetchMovieData();
        movieData.execute(address);
    }


    public static class FetchMovieData extends AsyncTask<String, Void, GridItem[]> {

        private final String TAG = FetchMovieData.class.getSimpleName();


        private JSONObject[] getMovieObjectFromJson(String moviesJsonStr) throws JSONException {

            JSONObject moviesIdJson = new JSONObject(moviesJsonStr);
            JSONArray moviesResultsArray = moviesIdJson.getJSONArray("results");



            JSONObject[] moviesResultObject = new JSONObject[20];

            for (int i = 0; i < 20; i++) {
                moviesResultObject[i] = moviesResultsArray.getJSONObject(i);
            }

            return moviesResultObject;
        }


        @Override
        protected GridItem[] doInBackground(String... address) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieJsonStr;


            try {

                URL url = new URL(address[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;


                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                movieJsonStr = buffer.toString();


            } catch (IOException e) {
                Log.e(TAG, "Error ", e);

                return null;

            } finally {

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }

            }

            try {
                JSONObject[] finalMovieObjectJson = getMovieObjectFromJson(movieJsonStr);

                GridItem[] finalMovieItem = new GridItem[20];


                for (int i = 0; i < 20; i++) {
                    finalMovieItem[i] = new GridItem(finalMovieObjectJson[i]);
                }

                return finalMovieItem;

            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);

                e.printStackTrace();
            }

            return null;
        }



        protected void onPostExecute(GridItem[] result) {

            if (result != null) {

                gridAdapter.clear();

                for (int i = 0; i < 20; i++) {
                    moviesId[i] = result[i];
                    gridAdapter.add(moviesId[i]);
                }
            }
        }



    }
}
