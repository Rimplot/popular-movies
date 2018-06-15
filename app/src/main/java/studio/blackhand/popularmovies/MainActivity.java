package studio.blackhand.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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
    private static Integer resultsPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.main_grid);

        adapter = new ImageAdapter(this, new ArrayList<Movie>());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Movie movie = adapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.KEY_MOVIE, movie);
                MainActivity.this.startActivity(intent);
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if(firstVisibleItem + visibleItemCount >= totalItemCount){
                    // End has been reached
                    updateResults();
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // update results only if mode changed
        if (id == R.id.button_popular && !mode.equals(NetworkUtils.MODE_POPULAR)) {
            mode = NetworkUtils.MODE_POPULAR;
            resultsPage = 0;
            adapter.clear();
            updateResults();
        } else if (id == R.id.button_top_rated && !mode.equals(NetworkUtils.MODE_TOP_RATED)) {
            mode = NetworkUtils.MODE_TOP_RATED;
            resultsPage = 0;
            adapter.clear();
            updateResults();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                /* If no arguments were passed, we don't have a query to perform. Simply return. */
                if (args == null) {
                    return;
                }

                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);

                forceLoad();
            }

            @Override
            public String loadInBackground() {
                String queryUrlString = args.getString(QUERY_URL_EXTRA_KEY);

                try {
                    URL queryUrl = new URL(queryUrlString);
                    return NetworkUtils.getResponseFromHttpUrl(queryUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        resultsPage++;  // stores the id of the page queried most recently

        ArrayList<Movie> movies = JsonUtils.parseJson(data);

        adapter.allMovies.addAll(movies);
        adapter.notifyDataSetChanged();

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void updateResults() {
        if (NetworkUtils.isOnline(this)) {
            String queryString = NetworkUtils.buildQueryUrlString(mode, resultsPage + 1);
            Log.e("URL", queryString);

            Bundle queryBundle = new Bundle();
            queryBundle.putString(QUERY_URL_EXTRA_KEY, queryString);

            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<String> movieQueryLoader = loaderManager.getLoader(MOVIE_LOADER);

            if (movieQueryLoader == null) {
                loaderManager.initLoader(MOVIE_LOADER, queryBundle, MainActivity.this);
            } else {
                loaderManager.restartLoader(MOVIE_LOADER, queryBundle, MainActivity.this);
            }
        } else {
            Toast.makeText(this, R.string.error_no_connection, Toast.LENGTH_SHORT).show();
        }
    }
}
