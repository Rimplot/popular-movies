package studio.blackhand.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import studio.blackhand.popularmovies.utilities.ImageAdapter;

public class DetailsActivity extends AppCompatActivity {

    public static final String KEY_TITLE = "movie_title";
    public static final String KEY_RELEASE_DATE = "movie_release_date";
    public static final String KEY_POSTER_PATH = "movie_poster_path";
    public static final String KEY_VOTE_AVERAGE = "movie_vote_average";
    public static final String KEY_PLOT_SYNOPSIS = "movie_plot_synopsis";

    private static final String POSTER_WIDTH = "original";

    String movieTitle;
    String movieReleaseDate;
    String moviePosterPath;
    Double movieVoteAverage;
    String moviePlotSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        movieTitle = intent.getStringExtra(KEY_TITLE);
        movieReleaseDate = intent.getStringExtra(KEY_RELEASE_DATE);
        moviePosterPath = intent.getStringExtra(KEY_POSTER_PATH);
        movieVoteAverage = intent.getDoubleExtra(KEY_VOTE_AVERAGE, 0);
        moviePlotSynopsis = intent.getStringExtra(KEY_PLOT_SYNOPSIS);

        String posterUri = ImageAdapter.POSTER_URI_STRING + POSTER_WIDTH + moviePosterPath;

        ImageView moviePoster = findViewById(R.id.movie_poster);
        Picasso
            .get()
            .load(posterUri)
            .into(moviePoster, new Callback() {
                @Override
                public void onSuccess() {
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvReleaseDate = findViewById(R.id.tv_release_date);
        TextView tvVoteAverage = findViewById(R.id.tv_vote_average);
        TextView tvPlotSynopsis = findViewById(R.id.tv_plot_synopsis);

        String rating = new DecimalFormat("#.#").format(movieVoteAverage) + "/10";

        tvTitle.setText(movieTitle);
        tvReleaseDate.setText(movieReleaseDate);
        tvVoteAverage.setText(rating);
        tvPlotSynopsis.setText(moviePlotSynopsis);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();  // simulate back button press, do not reload the whole activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
