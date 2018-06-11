package studio.blackhand.popularmovies;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import studio.blackhand.popularmovies.model.Movie;
import studio.blackhand.popularmovies.utilities.ImageAdapter;
import studio.blackhand.popularmovies.utilities.JsonUtils;
import studio.blackhand.popularmovies.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    private static final int MOVIE_LOADER = 163;
    private static final String QUERY_URL_EXTRA_KEY = "query_url";

    private static String mode = NetworkUtils.MODE_POPULAR;

    public static List<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportLoaderManager().initLoader(MOVIE_LOADER, null, this);

        String queryString = NetworkUtils.buildQueryUrlString(mode);
        Log.e("mittomén", queryString);

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
            // COMPLETED (1) Create a String member variable called mGithubJson that will store the raw JSON
            /* This String will contain the raw JSON from the results of our Github search */
            String mJson;

            @Override
            protected void onStartLoading() {
                /* If no arguments were passed, we don't have a query to perform. Simply return. */
                if (args == null) {
                    return;
                }

                // COMPLETED (2) If mGithubJson is not null, deliver that result. Otherwise, force a load
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
        if (null == data) {Log.e("Error!", "Franc tudja miért üres a data...");
        } else {
            Log.i("output", data);
        }

        movies = JsonUtils.parseJson(data);

        Log.w("Movies:", movies.get(0).getPosterPath());

        GridView gridView = findViewById(R.id.main_grid);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
