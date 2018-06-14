package studio.blackhand.popularmovies;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import studio.blackhand.popularmovies.model.Movie;
import studio.blackhand.popularmovies.utilities.ImageAdapter;
import studio.blackhand.popularmovies.utilities.JsonUtils;
import studio.blackhand.popularmovies.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    private static final int MOVIE_LOADER = 163;
    private static final String QUERY_URL_EXTRA_KEY = "query_url";

    private static String mode = NetworkUtils.MODE_POPULAR;

    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String queryString = NetworkUtils.buildQueryUrlString(mode);

        Bundle queryBundle = new Bundle();
        queryBundle.putString(QUERY_URL_EXTRA_KEY, queryString);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> movieQueryLoader = loaderManager.getLoader(MOVIE_LOADER);
        if (movieQueryLoader == null) {
            loaderManager.initLoader(MOVIE_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(MOVIE_LOADER, queryBundle, this);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            String mJson;

            @Override
            protected void onStartLoading() {
                /* If no arguments were passed, we don't have a query to perform. Simply return. */
                if (args == null) {
                    return;
                }

                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);

                /*
                 * If we already have cached results, just deliver them now. If we don't have any
                 * cached results, force a load.
                 */
                if (mJson != null) {
                    deliverResult(mJson);
                } else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                String queryUrlString = args.getString(QUERY_URL_EXTRA_KEY);

                try {
                    URL queryUrl = new URL(queryUrlString);
                    String queryResult = NetworkUtils.getResponseFromHttpUrl(queryUrl);
                    return queryResult;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(String json) {
                mJson = json;
                super.deliverResult(json);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        ArrayList<Movie> movies = JsonUtils.parseJson(data);

        GridView gridView = findViewById(R.id.main_grid);
        adapter = new ImageAdapter(this, movies);
        gridView.setAdapter(adapter);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Movie movie = adapter.getItem(position);
                Toast.makeText(MainActivity.this, movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
